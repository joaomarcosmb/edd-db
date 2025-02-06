package org.edd.model.structures;

public class NodeStack {
    public enum ActionType {
        INSERT, UPDATE, DELETE
    }

    public DataNode dataNode;
    protected NodeStack next;
    public ActionType actionType;

    public NodeStack(DataNode dataNode, ActionType actionType) {
        this.dataNode = dataNode;
        this.actionType = actionType;
        this.next = null;
    }
}
