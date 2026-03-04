package de.mehtrick.bjoern.doc;

/**
 * Represents a single entry in the git history of a spec file.
 */
public class GitHistoryEntry {

    private final String date;
    private final String author;
    private final String message;

    public GitHistoryEntry(String date, String author, String message) {
        this.date = date;
        this.author = author;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }

    public String getMessage() {
        return message;
    }
}
