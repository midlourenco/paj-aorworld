import { APP_ERROR,CHANGE_LANGUAGE,LOGIN_OK, GET_LOGGED_USER, LOGOUT_OK, UNREAD_NOTIFICATION} from "./actionTypes";

//função que cria um objecto cujo o tipo é xxxx, e o payload é um objecto (ex: username:username)
/**
 * função que guarda o erro gerado
 * @param {} error 
 * @returns 
 */
export const setAppError = (error) => ({
  type: APP_ERROR,
  payload: { error }
});

/**
 * função que guarda o idioma seleccionado
 * @param {*} language 
 * @returns 
 */
export const setSelectedLanguage = (language) => ({
  type: CHANGE_LANGUAGE,
  payload: { language }
});

/**
 * função que guarda token do utilizador logado
 * @param {*}  
 * @returns 
 */
 export const setLoginOK = (token) => ({
  type: LOGIN_OK,
  payload: { token }
});

/**
 * função que guarda info do utilizador logado
 * @param {*}  
 * @returns 
 */
 export const setLoggedUser = (firstName,userPriv,userId) => ({
  type: GET_LOGGED_USER,
  payload: { firstName,userPriv,userId }
});

/**
 * função que guarda info do utilizador logado
 * @param {*}  
 * @returns 
 */
 export const setLogout = (firstName, userPriv,token,userId) => ({
  type: LOGOUT_OK,
  payload: { firstName ,userPriv,token,userId },

});

/**
 * função que guarda info do utilizador logado
 * @param {*}  
 * @returns 
 */
 export const setNotifNumber = (unreadNotif) => ({
  type: UNREAD_NOTIFICATION,
  payload: { unreadNotif },

});