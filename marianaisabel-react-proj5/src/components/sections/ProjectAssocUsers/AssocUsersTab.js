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
import FormUserToAssoc from "./FormUserToAssoc"
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';

//TODO: 
function setAppError(error){
    console.log(error)
}

const AssocUsersTab=({ users,projectId,removeUserAssociated,...props})=>{
    const navigate = useNavigate();
    const intl = useIntl();

    const {register, watch,handleSubmit, formState: {errors}}= useForm();

    const { get, post, del, response, loading, error } = useFetch();
   
    const { id } = useParams();
    console.log(id)



        /**** *******************************************STATE******************************************************** */
        const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
        const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    


    return (<>                
            <Heading as="h3" ><FormattedMessage id={"users_to_associate"} />:</Heading>
          
                <TableContainer>
                <Table >
                <Thead>
                <Tr>
                    <Th>{intl.formatMessage({id: 'full_name'})}</Th>
                    <Th>{intl.formatMessage({id: 'form_field_email'})}</Th>
                    <Th>{intl.formatMessage({id: 'job'})}</Th>
                    <Th>{intl.formatMessage({id: 'associate'})}</Th>
                </Tr>
                </Thead>
                <Tbody>
                {users.map((user)=>(
                    <Td>
                        <FormUserToAssoc key={user.id} user={user} projectId={projectId} removeUserAssociated={removeUserAssociated} />
                    </Td>
                ))}  
            
                </Tbody>
                </Table>
                </TableContainer>
                


            </>


            
        )
    }

export default AssocUsersTab