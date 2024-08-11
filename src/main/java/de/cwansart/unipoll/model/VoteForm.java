package de.cwansart.unipoll.model;

import java.util.List;

public class VoteForm {
    private List<String> selected;

    public VoteForm() {
    }

    public VoteForm(List<String> selected) {
        this.selected = selected;
    }

    public List<String> getSelected() {
        return selected;
    }

    public void setSelected(List<String> selected) {
        this.selected = selected;
    }
}