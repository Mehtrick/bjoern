package de.mehtrick.bjoern.doc;

/**
 * Represents a single entry in the git history of a spec file.
 */
public class GitHistoryEntry {

    private final String date;
    private final String committer;
    private final String message;

    public GitHistoryEntry(String date, String committer, String message) {
        this.date = date;
        this.committer = committer;
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public String getCommitter() {
        return committer;
    }

    public String getMessage() {
        return message;
    }
}
