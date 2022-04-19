import { APP_ERROR } from "../actionTypes";

const initialState = {
    error: ""
};

export default function(state = initialState, action) {

    switch (action.type) {
        case APP_ERROR: {
            return {...state, 
            error: action.payload.error}
        }
        default: {
            return state;
        }
    }

};
