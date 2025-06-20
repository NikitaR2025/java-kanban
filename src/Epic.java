import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<Integer> subtasksIds;

    public Epic(String title, String description) {
        super(title, description);
        subtasksIds = new ArrayList<>();
    }

    public ArrayList<Integer> getSubtasksIds() {
        return subtasksIds;
    }

    public void setSubtasksIds(ArrayList<Integer> subtasksIds) {
        this.subtasksIds = subtasksIds;
    }

    @Override
    public String toString() {
        String result = super.toString() + ", содержит id Subtask: " + subtasksIds.toString();
        return result;
    }
}
