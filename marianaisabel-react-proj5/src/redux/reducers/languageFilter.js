import { CHANGE_LANGUAGE} from "../actionTypes";

function selectedLang(){
    let langLS= localStorage.getItem("selectedLang");
    console.log("lang do browser " + navigator.language)
    console.log("lang da LS " + langLS)

    if (langLS!=null){
          return langLS;
    }else {
        let browserLang =navigator.language;
        if(browserLang =="pt-PT" || browserLang =="pt" || browserLang =="pt-BR" ){
            return "pt";
        }else{
            return "en"
        }
    }
}

const initialState = {
    language: selectedLang(),
  };



  /**
   * função dos reducers relacionados com a alteração de idioma
   * @param {*} state 
   * @param {*} action 
   * @returns 
   */
export default function(state = initialState, action) {

  switch (action.type) {
    case CHANGE_LANGUAGE: {
      return {...state, 
        language: action.payload.language}
    }
   
    default: {
      return state;
    }
  }
};