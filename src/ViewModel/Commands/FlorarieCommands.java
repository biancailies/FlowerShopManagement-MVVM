package ViewModel.Commands;

import ViewModel.FlorarieVM;

public class FlorarieCommands implements ICommand {
    private FlorarieVM viewModel;
    private CommandType commandType;

    public enum CommandType{
        ADD_FLORARIE,
        UPDATE_FLORARIE,
        DELETE_FLORARIE,

        ADD_FLOARE,
        UPDATE_FLOARE,
        DELETE_FLOARE,

        ADD_STOC,
        UPDATE_STOC,
        DELETE_STOC,

        SEARCH_DENUMIRE,
        FILTER_COLOR,
        FILTER_DISP
    }

    public FlorarieCommands(FlorarieVM viewModel, CommandType commandType) {
        this.viewModel = viewModel;
        this.commandType = commandType;
    }

    @Override
    public void execute(){
        switch (this.commandType){
            case ADD_FLORARIE:
                viewModel.addFlorarie();
                break;
            case UPDATE_FLORARIE:
                viewModel.updateFlorarie();
                break;
            case DELETE_FLORARIE:
                viewModel.deleteFlorarie();
                break;
            case ADD_FLOARE:
                viewModel.addFloare();
                break;
            case UPDATE_FLOARE:
                viewModel.updateFloare();
                break;
            case DELETE_FLOARE:
                viewModel.deleteFloare();
                break;
            case ADD_STOC:
                viewModel.addStoc();
                break;
            case UPDATE_STOC:
                viewModel.updateStoc();
                break;
            case DELETE_STOC:
                viewModel.deleteStoc();
                break;
            case SEARCH_DENUMIRE:
                viewModel.searchFloare();
                break;
            case FILTER_COLOR:
                viewModel.filterFloriCuloare();
                break;
            case FILTER_DISP:
                viewModel.filterFloriDisp();
                break;
        }
    }

}
