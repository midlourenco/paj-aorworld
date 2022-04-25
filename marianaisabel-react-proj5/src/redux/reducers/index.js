import { combineReducers } from "redux";
import errorMsg from "./errorMsg";
import selectedLanguage from "./languageFilter";
import loginOK from "./loginOK"

//o   conteudo do state global. agrega todos os reducers, é onde o store se liga para saber quais os reducers tem que aceder
export default combineReducers({ loginOK,selectedLanguage, errorMsg});
