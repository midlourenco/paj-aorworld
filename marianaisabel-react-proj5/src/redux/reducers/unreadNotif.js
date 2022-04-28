import { UNREAD_NOTIFICATION } from "../actionTypes";

//TODO:numero de notificações para já fica iniciado a zero.qdo se fizer refresh perde-se a info. 
const initialState = {
    unreadNotif: 0
};

export default function(state = initialState, action) {
    switch (action.type) {
        case UNREAD_NOTIFICATION: {
            return {...state, 
                unreadNotif: action.payload.unreadNotif}
        }
        default: {
            return state;
        }
    }
};