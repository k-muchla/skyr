package com.example.skyr.note;

import com.example.skyr.InstantFactory;
import com.example.skyr.exception.NotFoundException;
import com.example.skyr.pagination.PaginatedResult;
import com.example.skyr.pagination.Pagination;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteDao noteDao;

    public UUID add(final String title, final String content) {
        final var id = UUID.randomUUID();
        noteDao.create(id, title, content);
        return id;
    }

    public void remove(final UUID id) {
        final var result = noteDao.delete(id);
        if (!result) throw new NotFoundException("Note not found");
    }

    public Note get(final UUID id) {
        final var note = noteDao.read(id);
        if (note == null) {
            throw new NotFoundException("Note not found");
        }
        return note;
    }

    public PaginatedResult<Note> get(final Pagination pagination) {
        return noteDao.read(pagination);
    }

    public void update(final UUID noteId, final String title, final String content) {
        noteDao.update(noteId, title, content, null, InstantFactory.create());
    }
}
