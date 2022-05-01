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
    Image,
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
} from "@chakra-ui/react";
import { ExternalLinkIcon, CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"
//TODO: 
function setAppError(error){
    console.log(error)
}
const AssocUsersTab=({isAdmin,...props})=>{
    const navigate = useNavigate();
    const intl = useIntl();


    const { get, post, del, response, loading, error } = useFetch();

    //const [currentId, setCurrentId]=useState("");
    const { id } = useParams();
    console.log(id)
   // setCurrentId(id)



        /**** *******************************************STATE******************************************************** */
  
        const [users, setUsers]=useState([])
        const [usersOnProject, setUsersOnProject]=useState([])
        const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    
    /**** ******************************************USE EFFECT********************************************************* */
 
    useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");

        const usersOnProject = await get("projects/"+id+"/assocUsersList")
        if (response.ok) {
            console.log(usersOnProject)
            setUsersOnProject(usersOnProject);
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
            <Heading as="h3" ><FormattedMessage id={"associated_users"} />:</Heading>

                <TableContainer>
                <Table >
                <Thead>
                <Tr>
                    <Th>{intl.formatMessage({id: 'user_name'})}</Th>
                    <Th>{intl.formatMessage({id: 'form_field_email'})}</Th>
                    <Th>{intl.formatMessage({id: 'job'})}</Th>
                </Tr>
                </Thead>
                <Tbody>
                {usersOnProject.map((u)=>(
                    <Tr key={u.id}>
                    <Td color="gray.500" fontWeight="400" >{u.firstName + " " + u.lastName}</Td>
                    <Td color="gray.500" fontWeight="400" >{u.email}</Td>
                    <Td color="gray.500" fontWeight="400" >{u.userRoleInProject}</Td>
                    <Td>
                        <IconButton onClick={()=> navigate("/users/"+u.id)} aria-label={intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} />
                    </Td>

                </Tr>
                ))}  
                </Tbody>
                </Table>
                </TableContainer>


            </>


            
        )
    }

export default AssocUsersTab