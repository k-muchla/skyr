package com.example.skyr.note.sync;

import com.example.skyr.note.Note;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record NotesSyncResult(UUID syncId, Instant syncTime, List<Note> modifiedNotes) {
}
