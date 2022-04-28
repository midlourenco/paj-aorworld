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
import EditableControls from "../../EditableControls"

const ProfileViewMode=({isAdmin, currentUser,editMode,handleEditClick,handleCancelClick, handleDeleteClick})=>{
    const intl = useIntl();
    return (<Box>
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
            >
                <Box>
                {console.log(currentUser)}                            
                    <Text fontSize='2xl' fontWeight={"bold"}> {currentUser.firstName +" "+ currentUser.lastName}</Text>
                    <Text as="i" fontSize='md'mt={1}  > {currentUser.email }</Text>
                    <br />
                    <Badge mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentUser.privileges || "-"} defaultMessage={"-"} /></Badge>
                </Box>
                <Box>
                    <Text fontSize='lg'> {currentUser.biography  }</Text>
                </Box>
                <Box>
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    <EditableControls isAdmin={isAdmin} editMode={editMode}  handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} handleCancelClick={handleCancelClick} />
                </Box>
            </Flex>
            
        </Box>
    </Stack>
    </Box>
    )
}

export default ProfileViewMode

