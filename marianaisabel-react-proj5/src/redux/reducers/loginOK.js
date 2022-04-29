import { LOGIN_OK, LOGOUT_OK,GET_LOGGED_USER} from "../actionTypes";

function getfirstName(){
    let firstNameLS= localStorage.getItem("firstName");
    console.log("name da LS " + firstNameLS)
    if (firstNameLS!=null){
        return firstNameLS;
    }else {  
        return ""
    }
}

function getPrivileges(){
    let privLS= localStorage.getItem("privileges");
    console.log("privileges da LS " + privLS)
    if (privLS!=null){
        return privLS;
    }else {  
        return "VIEWER"
    }
}

function getToken(){
    let tokenLS= localStorage.getItem("Authorization");
    console.log("name da LS " + tokenLS)
    if (tokenLS!=null){
        return tokenLS;
    }else {  
        return ""
    }
}

function getUserId(){
    let userIdLS= localStorage.getItem("userId");
    console.log("name da LS " + userIdLS)
    if (userIdLS!=null){
        return userIdLS;
    }else {  
        return ""
    }
}
const initialState = {
    firstName: getfirstName(),
    userPriv:getPrivileges(),
    token:getToken(),
    userId:getUserId(),
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
                token:action.payload.token}
        }
        case GET_LOGGED_USER:{
            return {...state, 
            firstName: action.payload.firstName,
            userId: action.payload.userId,
            userPriv: action.payload.userPriv}
        }
        case LOGOUT_OK: {
            return {...state, 
                firstName: action.payload.firstName,
                userPriv: action.payload.userPriv,
                userId: action.payload.userId,
                token:action.payload.token}   
        }
        default: {
            return state;
        }
    }
};