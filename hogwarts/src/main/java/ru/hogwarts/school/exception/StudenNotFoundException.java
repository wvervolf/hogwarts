package ru.hogwarts.school.exception;

public class StudenNotFoundException extends NotFoundException {

    public StudenNotFoundException(long id) {
        super(id);
    }

    @Override
    public String getMessage() {
        return "Студент с id = %d не найден!".formatted(getId());
    }
}
