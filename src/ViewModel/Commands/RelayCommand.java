package ViewModel.Commands;

public class RelayCommand implements ICommand {

    private final Runnable action;

    public RelayCommand(Runnable action) {
        this.action = action;
    }

    @Override
    public void execute() {
        if (action != null) {
            action.run();
        }
    }
}