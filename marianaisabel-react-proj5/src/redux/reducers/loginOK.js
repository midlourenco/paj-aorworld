import { LOGIN_OK, LOGOUT_OK} from "../actionTypes";

const initialState = {
    firstname: "",
    adminPriv:false,
    token:"",
  };

  /**
   * função dos reducers relacionados com o login e o utilizador logado
   * @param {*} state 
   * @param {*} action 
   * @returns 
   */
export default function(state = initialState, action) {

    switch (action.type) {
        case LOGIN_OK: {
            return {...state, 
                firstname: action.payload.firstname,
                adminPriv: action.payload.adminPriv,
                token:action.payload.token}
        }
        case LOGOUT_OK: {
            return {...state, 
                firstname: "",
                adminPriv: false,
                token:""}   
        }
        default: {
            return state;
        }
    }
};