package pl.wsb.issuetracker.jpa.entity;

import java.util.Arrays;

public enum IssueStatus {

    ASSIGNED, IN_PROGRESS, RESOLVED, CANCELLED, DECLINED;

    private IssueStatus[] acceptedNextStatus;

    static {
        ASSIGNED.acceptedNextStatus = new IssueStatus[]{IN_PROGRESS, DECLINED};
        IN_PROGRESS.acceptedNextStatus = new IssueStatus[]{ASSIGNED, RESOLVED, CANCELLED};
        RESOLVED.acceptedNextStatus = new IssueStatus[]{};
        CANCELLED.acceptedNextStatus = new IssueStatus[]{};
        DECLINED.acceptedNextStatus = new IssueStatus[]{};
    }

    public boolean canSwitchTo(final IssueStatus status) {
        return Arrays.asList(this.acceptedNextStatus).contains(status);
    }
}
