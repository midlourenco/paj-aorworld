import React from 'react'
import { useState, useEffect, } from "react";
import {FormattedMessage ,useIntl} from "react-intl";
import { useForm } from "react-hook-form";
import {Navigate, useParams, useNavigate} from 'react-router-dom'
import useFetch from 'use-http';
import {
    Flex,
    Heading,
    Input,
    Button,
    Stack,
    Box,
    Text,
    Table,
    Thead,
    Tbody,
    Tfoot,
    Tr,
    Th,
    Td,
    TableCaption,
    TableContainer,
} from "@chakra-ui/react";
import { connect } from 'react-redux'


//TODO: 
function setAppError(error){
    console.log(error)
}

  //**********************************************MAIN FUNCTION !!!!!*************************************************************************** */

function ProjectAssocUsers({userId, userPriv,...props}) {

    const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    const navigate = useNavigate();
    //const [currentId, setCurrentId]=useState("");
    const { id } = useParams();
    console.log(id)
   // setCurrentId(id)



        /**** *******************************************STATE******************************************************** */
        const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
        const [users, setUsers]=useState([])
        const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    
    /**** ******************************************USE EFFECT********************************************************* */
 
    useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");

        const getUsers = await get("users/")
        if (response.ok) {
            console.log(getUsers)
            setUsers(getUsers);
            if(userPriv =="ADMIN"){
                setAdminPriv(true);
            }
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
        
        
    },[])






  return (<>
    <Stack 
    spacing={10} 
    backgroundColor="gray.200" 
    pb={20}
    minHeight="83vh"
    >
        <Stack
        flexDir="column"
        mb="2"
        justifyContent="center"
        alignItems="center"
        >
            
            <Box minW={{ base: "90%", md: "468px" }} mb={5}>
                <Flex
                spacing={2}
                backgroundColor="whiteAlpha.900"
                boxShadow="md"
                minHeight={"300px"}
                p={10}
                flexDirection={"column"}
                justifyContent={"space-between"}
                mt={10}
                ></Flex>
                {restResponse==""?
                <div>ProjectAssocUsers</div>
                :restResponse=="OK"?
                <Text my={5} color="green">{intl.formatMessage({id: 'sucess_rest_response'})}</Text>
                : restResponse=="NOK"?
                  <Text my={5} color="red"> {intl.formatMessage({id: 'error_rest_response'})}: {error} </Text>
                  :null
            }   

    </Box>
    </Stack>

    </Stack>
    </>
  )
}
const mapStateToProps = state => {
    return { userPriv: state.loginOK.userPriv,
            userId:state.loginOK.userId,
            errorTopBar: state.errorMsg.errorTopBar
    };
  };
export default connect(mapStateToProps,{})( ProjectAssocUsers)