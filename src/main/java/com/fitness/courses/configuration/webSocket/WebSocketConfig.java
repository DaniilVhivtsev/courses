package com.fitness.courses.configuration.webSocket;

import static com.fitness.courses.configuration.security.jwt.JwtRequestFilter.AUTHORIZATION;
import static com.fitness.courses.configuration.security.jwt.JwtRequestFilter.JWT_PREFIX;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import com.fitness.courses.configuration.security.jwt.JwtProvider;
import com.fitness.courses.configuration.security.jwt.util.JwtUtils;
import com.fitness.courses.http.user.service.UserService;

import io.jsonwebtoken.Claims;

@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer
{
    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserService userService;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry)
    {
        registry.addEndpoint("/fitness_online_web_socket")
                .setAllowedOrigins("http://localhost:3000")
                .withSockJS();

        registry.addEndpoint("/mobile_fitness_online_web_socket")
                .setAllowedOriginPatterns("*");
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry)
    {
        registry.enableSimpleBroker("/chat");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelInterceptor() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                final StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

                if (accessor == null)
                {
                    return message;
                }

                final StompCommand command = accessor.getCommand();

                if (StompCommand.CONNECT.equals(command) || StompCommand.SEND.equals(command) )
                {
                    final Optional<String> tokenOptional = getAccessTokenFromRequest(accessor);

                    if (tokenOptional.isPresent() && jwtProvider.validateAccessToken(tokenOptional.get()))
                    {
                        final Claims claims = jwtProvider.getAccessClaims(tokenOptional.get());
                        final UserDetails userDetails = userService.loadUserByUsername(JwtUtils.getUsername(claims));
                        final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails == null
                                        ? List.of()
                                        : userDetails.getAuthorities()
                        ); // in constructor set authenticated true

                        accessor.setUser(authentication);
                    }
                }

                return message;
            }
        });
    }


    /**
     * Получение токена из запроса по заголовку "Authorization".
     *
     * @param accessor STOMP запрос.
     * @return access токен, иначе пустой Optional.
     */
    private static Optional<String> getAccessTokenFromRequest(StompHeaderAccessor accessor)
    {
        final String bearer = accessor.getFirstNativeHeader(AUTHORIZATION);
        if (StringUtils.hasText(bearer) && bearer.startsWith(JWT_PREFIX))
        {
            return Optional.of(bearer.substring(JWT_PREFIX.length()));
        }

        return Optional.empty();
    }
}
