import { createStore } from "redux";
import rootReducer from "./reducers"; // componente mto importante pq vai definir as coisas que são guardadas, componente de redux que vai envolver o componente de react
import { composeWithDevTools } from "redux-devtools-extension"; //extensão que vai permitir usar a extensão do redux no browser

/**
 * store do redux (guardo estado globais, de todas as variáveis geridas pelos reducers)
 */
export default createStore(rootReducer,composeWithDevTools() );
//export default createStore(rootReducer);
