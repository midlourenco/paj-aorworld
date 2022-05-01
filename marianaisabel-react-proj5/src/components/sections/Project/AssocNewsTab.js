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
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';

//TODO: 
function setAppError(error){
    console.log(error)
}
const AssocNewsTab=({isAdmin, currentProject,...props})=>{
    const navigate = useNavigate();
    const intl = useIntl();
    return (<>                
            <Heading pt={10} as="h3" ><FormattedMessage id={"associated_news"} />:</Heading>

                <TableContainer>
                <Table >
                    <Thead>
                    <Tr>
                        <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
                        <Th>{intl.formatMessage({id: 'create_by'})}</Th>
                        <Th><FormattedMessage id={"form_field_create_date"}/></Th>

                    </Tr>
                    </Thead>
                    <Tbody>
                    {currentProject.associatedNews.map((n)=>(
                        <Tr key={n.id}>
                        <Td color="gray.500" fontWeight="400" >{n.title}</Td>
                        <Td color="gray.500" fontWeight="400" >{n.createdBy.firstName}</Td>
                        <Td color="gray.500" fontWeight="400"  textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(n.createdDate)}} /> </Td>
                        <ButtonGroup>
                        <Tooltip label={intl.formatMessage({id: 'tooltip_desassociate'})}>
                            <Button maxW="130px" colorScheme={"teal"} fontSize="sm" leftIcon={<FaUserTimes />} > <FormattedMessage id={"desassociate"}/></Button>
                        </Tooltip>
                        </ButtonGroup>
                        <Td>
                        <Tooltip label={"/news/"+n.id}>
                            <IconButton onClick={()=> navigate("/news/"+n.id)} aria-label={intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} />
                        </Tooltip>
                        </Td>
                    </Tr>
                    ))}  
                    </Tbody>
                </Table>
                </TableContainer>

            </>


            
        )
    }

export default AssocNewsTab