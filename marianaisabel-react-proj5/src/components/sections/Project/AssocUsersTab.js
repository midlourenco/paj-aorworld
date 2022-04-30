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

const AssocUsersTab=({isAdmin, currentProject,...props})=>{
    const navigate = useNavigate();
    const intl = useIntl();
    return (<>                
            <Heading as="h3" ><FormattedMessage id={"associated_users"} />:</Heading>

                <TableContainer>
                <Table >
                <Thead>
                <Tr>
                    <Th>{intl.formatMessage({id: 'user_name'})}</Th>
                    <Th>{intl.formatMessage({id: 'form_field_email'})}</Th>
                </Tr>
                </Thead>
                <Tbody>
                {currentProject.associatedUsers.map((u)=>(
                    <Tr key={u.id}>
                    <Td color="gray.500" fontWeight="400" >{u.firstName + " " + u.lastName}</Td>
                    <Td color="gray.500" fontWeight="400" >{u.email}</Td>
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