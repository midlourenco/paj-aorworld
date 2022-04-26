import React, { useEffect } from "react"
//Redirect replace by Naviagate
import {Navigate, useNavigate} from 'react-router-dom'
import config from "../config";
import useFetch from 'use-http';
import { setLogout, setAppError } from '../redux/actions'
import { connect } from 'react-redux'

function Logout({setLogout,...props}) {
     // console.log(props);
   // props.history
   //exemplo para redireccionar para uma página dada uma dada condição:
    // if(true){
    //     return <Navigate to="/news" />
    // }
    const navigate = useNavigate();

    useEffect(() => {
      sessionStorage.clear();
      localStorage.clear();
      setLogout("","VIEWER","")
      navigate("/")
    }, [])
    
// <Navigate to="/" />
  return (
  
    <>
    </>
    
  )
}
const mapStateToProps = state => {
  return { error: state.errorMsg.error, // fazemos a logica se ele existir mostra mensagem de erro
          token:state.loginOK.token,
          firstName:state.loginOK.firstName,
          userPriv:state.loginOK.userPriv,
  };
};
export default connect(mapStateToProps, { setLogout}) (Logout)