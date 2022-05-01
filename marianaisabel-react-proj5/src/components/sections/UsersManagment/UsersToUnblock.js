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
    ButtonGroup 
} from "@chakra-ui/react";
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';
import { MdBlock} from 'react-icons/md';
import useFetch from 'use-http';
import { connect } from 'react-redux'

//TODO: 
function setAppError(error){
    console.log(error)
}

function UsersToUnblock({userPriv, ...props}) {
    const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    const navigate = useNavigate();
    const { id } = useParams();
    console.log(id)
    const scroll = { x: 1600, y: 300 };

    // const BlockedUserSymbol = chakra(FaUserTimes);
    // const CheckedUserSymbol = chakra(FaUserCheck);
    /**** *******************************************STATE******************************************************** */

    const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    ;
    const [usersToUnblock, setUsersToUnblock] = useState([]);

    /**** ****************************************FORM*********************************************************** */
    //fuções que chamos ao submeter o formulário de edição
    const {register, handleSubmit, formState: {errors}}= useForm();
    async function onSubmit (data, e) {
    }
     /**** ******************************************USE EFFECT********************************************************* */

    useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");

        const getUsersToUnblock = await get("users/deletedList")
        if (response.ok) {
            console.log(getUsersToUnblock)
            setUsersToUnblock(getUsersToUnblock);
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

  return (
    <Box minW={{ base: "90%", md: "468px" }} mb={0} >
    <Flex
    spacing={2}
    backgroundColor="whiteAlpha.900"
    boxShadow="md"
    minHeight={"300px"}
    p={[5,5,10]}
    flexDirection={"column"}
    justifyContent={"space-between"}
    mt={5}
        
    > 
    <Heading as='h3' size='lg' mt={3} color={"black"} ><FormattedMessage id={"users_blocked"} /> </Heading>
    {error && 'Error!'}
    {loading && 'Loading...'}
    <TableContainer  maxW={["280px","450px","100%"]} scroll={scroll} fontSize={["sm","sm","md"]} mx={2}>
        <Table >
            <Thead>
            <Tr>
                <Th><FormattedMessage id={"form_field_first_name"}/></Th>
                <Th><FormattedMessage id={"form_field_last_name"}/></Th>
                <Th><FormattedMessage id={"form_field_email"}/></Th>
                <Th><FormattedMessage id={"form_field_biography"}/></Th>
                <Th textAlign={"center"}><FormattedMessage id={"actions"}/></Th>
            </Tr>
            </Thead>
            <Tbody>
            {usersToUnblock.map((u)=>(
                <Tr key={u.id}>
                <Td>{u.firstName}</Td>
                <Td>{u.lastName}</Td>
                <Td>{u.email}</Td>
                
                <Td minW="100px" maxW="200px"  isTruncated>
                    <Tooltip label={u.biography}>
                        {u.biography}
                    </Tooltip>
                </Td>
                <Td>
                    <ButtonGroup>
                        <Tooltip label={intl.formatMessage({id: 'tooltip_unblock_user'})}>
                        <Button maxW="130px" colorScheme={"teal"} fontSize="sm" leftIcon={<FaUserCheck />} > <FormattedMessage id={"unblock"}/></Button>
                        </Tooltip>
                    
                    </ButtonGroup>
                </Td>
            </Tr>
            ))}  
            </Tbody>
        </Table>
    </TableContainer>
        

    {restResponse=="OK"?
    <Text mt={5} color="green"><FormattedMessage id={"sucess_on_updating"} /> </Text>
    : restResponse=="NOK"?
    <Text mt={5} color="red"> <FormattedMessage id={"error_on_updating"} />  </Text>
    :null
    }
    {/* <Box mb={30}>
        <RedirectButton path="/about" description={intl.formatMessage({id: "_back_to_team" })} />
    </Box > */}
    </Flex>
    </Box>

  )
}

export default UsersToUnblock;