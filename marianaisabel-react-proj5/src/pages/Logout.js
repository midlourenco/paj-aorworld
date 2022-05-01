import React, { useEffect,useState } from "react"
//Redirect replace by Naviagate
import {Navigate, useNavigate} from 'react-router-dom'
import config from "../config";
import useFetch from 'use-http';
import { setLogout, setAppError } from '../redux/actions'
import { connect } from 'react-redux'


function Logout({setLogout,errorTopBar="",...props}) {
     // console.log(props);
   // props.history
   //exemplo para redireccionar para uma página dada uma dada condição:
    // if(true){
    //     return <Navigate to="/news" />
    // }
    const navigate = useNavigate();
    const { get, post, del, response, loading, error } = useFetch();
    
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""

    useEffect(async() => {
      console.log("dentro do use effect do logout")
      const logoutRest = await post("users/logout")
      if (response.ok) {
          console.log(logoutRest)
          sessionStorage.clear();
          localStorage.clear();
          setLogout("","VIEWER","")
          navigate("/")
          setAppError("");
      } else if(response.status==401) {
          console.log("credenciais erradas? " + error)
          setAppError('error_fetch_login_401');
          setRestResponse("NOK");
      }else{
          console.log("houve um erro no fetch " + error)
          if(error && error!=""){
              setAppError(  error );
              setRestResponse("NOK");
          }else{
              setAppError(  "error_fetch_generic" );
              setRestResponse("NOK");
          }
      }
    }, [])
    
// <Navigate to="/" />
  return (
  
    <>
     {error && 'Error!'}
         {loading && 'Loading...'}

    </>
    
  )
}
const mapStateToProps = state => {
  return { errorTopBar: state.errorMsg.errorTopBar, // fazemos a logica se ele existir mostra mensagem de erro
          token:state.loginOK.token,
          firstName:state.loginOK.firstName,
          userPriv:state.loginOK.userPriv,
  };
};
export default connect(mapStateToProps, { setLogout}) (Logout)