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
import { CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'

function EditableControls({isAdmin,editMode,handleEditClick,handleCancelClick, handleDeleteClick}) {
    const intl = useIntl();
    

    return editMode ? (
        <Box  textAlign={"center"}>
            <ButtonGroup justifyContent='center' size='sm' >
                <Tooltip label= {intl.formatMessage({id: 'cancel'})} aria-label='A tooltip'>
                    <IconButton icon={<CloseIcon />}  onClick={handleCancelClick} mx={3} />
                </Tooltip>
                <Tooltip label= {intl.formatMessage({id: 'update'})} aria-label='A tooltip' >
                    <IconButton  type="submit" icon={<CheckIcon />}  mx={3}/>
                </Tooltip>
            
            {/* <CancelButton handleCancelClick={handleCancelClick} intl={intl}/>
                <SubmitButton intl={intl} /> */}
                
                {/* background="whiteAlpha.900" */}
            </ButtonGroup>
            </Box>
         ): (
        <Flex justifyContent='center' gap={5}>
            <Button mt={6} size='md' rightIcon={<EditIcon />}  colorScheme={"teal"} onClick={handleEditClick} > <FormattedMessage id={"edit"} defaultMessage={"-"}/></Button>
            <Button mt={6} size='md' rightIcon={<DeleteIcon />}  colorScheme={"teal"} onClick={handleDeleteClick} > <FormattedMessage id={"delete"} defaultMessage={"-"}/></Button>
        
            {/* <Tooltip label= {intl.formatMessage({id: 'delete'})} aria-label='A tooltip' >
                <IconButton  icon={<DeleteIcon />}  mx={3} onClick={handleDeleteClick}/>
            </Tooltip> */}
                
                
        </Flex>
        )
    }

function SubmitButton({type}){
    const intl = useIntl();
    return(
        <Tooltip type={type} label= {intl.formatMessage({id: 'update'})} aria-label='A tooltip' >
            <IconButton  type="submit" icon={<CheckIcon />}  mx={3}/>
        </Tooltip>
    )
}


function CancelButton({handleCancelClick} ){
    const intl = useIntl();

    return(
        <Tooltip label= {intl.formatMessage({id: 'cancel'})} aria-label='A tooltip'>
            <IconButton icon={<CloseIcon />}  onClick={handleCancelClick} mx={3} />
        </Tooltip>
    )
}


export default EditableControls