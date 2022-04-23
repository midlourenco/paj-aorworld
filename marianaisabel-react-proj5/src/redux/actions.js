import { APP_ERROR,CHANGE_LANGUAGE} from "./actionTypes";

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
