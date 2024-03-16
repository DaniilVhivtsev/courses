package com.fitness.courses.global.service.crud;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import com.fitness.courses.global.exceptions.NotFoundException;

public class AbstractCrudService<T, R extends JpaRepository<T, Long>>
{
    protected final R repository;
    private final Class<T> clazz;

    public AbstractCrudService(R repository, Class<T> clazz)
    {
        this.repository = repository;
        this.clazz = clazz;
    }

    @Transactional
    public T save(T entity)
    {
        return repository.save(entity);
    }

    @Transactional
    public T update(T entity)
    {
        return repository.save(entity);
    }

    @Transactional(readOnly = true)
    public Optional<T> findById(Long id)
    {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public T findByIdOrThrow(Long id) throws NotFoundException
    {
        return this.findById(id)
                .orElseThrow(
                        () -> new NotFoundException(("Can't find entity %s by id %s").formatted(clazz.getName(), id)));
    }

    @Transactional
    public void delete(T entity)
    {
        repository.delete(entity);
    }

    @Transactional
    public void deleteById(Long id)
    {
        repository.deleteById(id);
    }
}
