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
    IconButton,
    useEditableControls,
    InputRightElement,  
    Table,
    Thead,
    Tbody,
    Tfoot,
    Tr,
    Th,
    Td,
    TableCaption,
    TableContainer,
    Button, 
    ButtonGroup, 
    VStack
} from "@chakra-ui/react";
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';
import { MdBlock} from 'react-icons/md';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import RedirectButton from "../components/RedirectButton"
import UsersToApprove from "../components/sections/UsersManagment/UsersToApprove"
import UsersToUnblock from "../components/sections/UsersManagment/UsersToUnblock"
//TODO: 
function setAppError(error){
    console.log(error)
}

function UserManagement({userPriv,errorTopBar="", ...props}) {
    const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    const navigate = useNavigate();
    const { id } = useParams();
    console.log(id)

    // const BlockedUserSymbol = chakra(FaUserTimes);
    // const CheckedUserSymbol = chakra(FaUserCheck);
    /**** *******************************************STATE******************************************************** */

    const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    const [scrollDown, setScrollDown]=useState(false);
    const [usersToAprove, setUsersToAprove] = useState([]);

    /**** ****************************************FORM*********************************************************** */
    //fuções que chamos ao submeter o formulário de edição
    const {register, handleSubmit, formState: {errors}}= useForm();
    async function onSubmit (data, e) {
    }
     /**** ******************************************USE EFFECT********************************************************* */

    useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");

        const getUsersToAprove = await get("users/viwersList")
        if (response.ok) {
            console.log(getUsersToAprove)
            setUsersToAprove(getUsersToAprove);
            if(userPriv =="ADMIN"){
                setAdminPriv(true);
            }
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
    <Box  mb={0}>
          <Flex
        flexDirection="column"
        width="100wh"
        minHeight="83vh"
        backgroundColor="gray.200"
        justifyContent="center"
        alignItems="center"
        pb={20}
        
        >
            <Heading><FormattedMessage id={"user_mangement"} /> </Heading>
            {error && 'Error!'}
            {loading && 'Loading...'}
            <Flex flexDirection={"column"}  >
            <UsersToApprove />
            <UsersToUnblock />                
            </Flex>
           
        </Flex>
    </Box>

    );
}

const mapStateToProps = state => {
    return { userPriv: state.loginOK.userPriv,
            errorTopBar: state.errorMsg.errorTopBar
    };
};

export default connect(mapStateToProps,{})(UserManagement)

