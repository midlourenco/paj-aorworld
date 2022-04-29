import { APP_ERROR } from "../actionTypes";

const initialState = {
    errorTopBar: ""
};

export default function(state = initialState, action) {

    switch (action.type) {
        case APP_ERROR: {
            return {...state, 
                errorTopBar: action.payload.errorTopBar}
        }
        default: {
            return state;
        }
    }

};
