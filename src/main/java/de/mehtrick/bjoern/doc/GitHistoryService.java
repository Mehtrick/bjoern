package de.mehtrick.bjoern.doc;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service to retrieve the git history for a given spec file using JGit.
 */
public class GitHistoryService {

    private static final Logger log = LoggerFactory.getLogger(GitHistoryService.class);
    private static final String DATE_FORMAT = "dd.MM.yyyy";

    /**
     * Returns the git commit history for the given file path in ascending order (oldest first).
     *
     * @param filePath absolute or relative path to the spec file
     * @return list of {@link GitHistoryEntry} sorted oldest-first, or empty list if git history is unavailable
     */
    public List<GitHistoryEntry> getHistory(String filePath) {
        try {
            File specFile = new File(filePath).getAbsoluteFile();
            FileRepositoryBuilder builder = new FileRepositoryBuilder();
            Repository repository = builder.findGitDir(specFile).build();

            File workTree = repository.getWorkTree();
            String relativePath = workTree.toPath().relativize(specFile.toPath()).toString().replace(File.separatorChar, '/');

            List<GitHistoryEntry> entries = new ArrayList<>();
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

            try (Git git = new Git(repository)) {
                LogCommand logCommand = git.log().addPath(relativePath);
                for (RevCommit commit : logCommand.call()) {
                    PersonIdent author = commit.getAuthorIdent();
                    String date = sdf.format(author.getWhen());
                    String committer = author.getName();
                    String message = commit.getShortMessage();
                    entries.add(new GitHistoryEntry(date, committer, message));
                }
            }

            // JGit returns commits newest-first; reverse to get oldest-first
            Collections.reverse(entries);
            return entries;
        } catch (Exception e) {
            log.warn("Could not retrieve git history for file {}: {}", filePath, e.getMessage());
            return Collections.emptyList();
        }
    }
}
