import React from 'react'
import { useState, useEffect, } from "react";
//Redirect replace by Naviagate
import {Navigate, useParams, useNavigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";

import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    Box,
    Link,
    Select,
    Avatar,
    Badge,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    FormLabel,
    Text,
    Spacer,
    Textarea,
    Tooltip,
    Editable,
    EditableInput,
    EditableTextarea,
    EditablePreview,
    ButtonGroup,
    IconButton,
    useEditableControls,
    InputRightElement
} from "@chakra-ui/react";

import useFetch from 'use-http';
import { setNotifNumber} from '../redux/actions'
import { connect } from 'react-redux'
import RedirectButton from "../components/RedirectButton"
import RecentNotifications from "../components/sections/Notification/RecentNotifications"
import AllNotifications from "../components/sections/Notification/AllNotifications"
//TODO: 
function setAppError(error){
    console.log(error)
}

function Notification({setNotifNumber,errorTopBar="", ...props}) {
    const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    const navigate = useNavigate();


    /**** *******************************************STATE******************************************************** */

    const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    

    /**** ****************************************FORM*********************************************************** */
    //fuções que chamos ao submeter o formulário de edição
    const {register, handleSubmit, formState: {errors}}= useForm();
    async function onSubmit (data, e) {
    }
     /**** ******************************************USE EFFECT********************************************************* */

    useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");

        const unreadNotif = await get("notifications/unreadNumber")
        if (response.ok) {
           console.log(unreadNotif)
           setNotifNumber(unreadNotif);
           setAppError("");
            // if(userPriv =="ADMIN"){
            //     setAdminPriv(true);
            // }
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setRestResponse("NOK");
            setAppError('error_fetch_login_401');
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
    },[])

/**** ******************************************RENDER / RETURN PRINCIPAL ********************************************************* */

    return (
    <Box>
          <Flex
        flexDirection="column"
        width="100wh"
        minHeight="83vh"
        backgroundColor="gray.200"
        justifyContent="center"
        alignItems="center"
        >

        <Heading><FormattedMessage id={"user_mangement"} /> </Heading>
            {error && 'Error!'}
            {loading && 'Loading...'}
            <Flex flexDirection={"column"}  >
            <RecentNotifications />
            <AllNotifications />  
            </Flex>   

        </Flex>
    </Box>
    );
}

const mapStateToProps = state => {
    return { errorTopBar: state.errorMsg.errorTopBar
    };
};

export default connect(mapStateToProps,{setNotifNumber})(Notification)