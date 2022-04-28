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
import { CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../Profile/EditableControls"

const ProjectViewMode=({isAdmin, currentProject,editMode,handleEditClick,handleCancelClick, handleDeleteClick})=>{
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
            mt={10}
            >
                <Box  alignSelf={"center"}>
                {console.log(currentProject)}
                    <Image boxSize='200px' name={currentProject.title} src={currentProject.image} ></Image>            
                  
                    

                    {/* <Badge mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentProject.privileges || "-"} defaultMessage={"-"} /></Badge> */}
                </Box>
                <Box mb={7}>
                <Text fontSize='2xl' fontWeight={"bold"}> {currentProject.title }</Text>
                    <Text fontSize='lg'> {currentProject.description }</Text>
                </Box>
                <Box >
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    <EditableControls  isAdmin={isAdmin} editMode={editMode}  handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} handleCancelClick={handleCancelClick} />
                </Box>
                <Heading as="h3" ><FormattedMessage id={"associated_projects"} />:</Heading>

                    <TableContainer>
                    <Table >
                        <Thead>
                        <Tr>
                            <Th>Titulo</Th>
                            <Th>Criado por:</Th>
                        </Tr>
                        </Thead>
                        <Tbody>
                        {currentProject.associatedNews.map((p)=>(
                            <Tr key={p}>
                            <Td>{p.title}</Td>
                            <Td>{p.createdBy.firstName}</Td>
                        </Tr>
                        ))}  
                        </Tbody>
                    </Table>
                    </TableContainer>


                    <Heading as="h3" ><FormattedMessage id={"associated_users"} />:</Heading>

                    <TableContainer>
                    <Table >
                    <Thead>
                    <Tr>
                        <Th>Nome</Th>
                        <Th>Email:</Th>
                    </Tr>
                    </Thead>
                    <Tbody>
                    {currentProject.associatedUsers.map((u)=>(
                        <Tr key={u}>
                        <Td>{u.firstName + " " + u.lastName}</Td>
                        <Td>{u.email}</Td>
                    </Tr>
                    ))}  
                    </Tbody>
                    </Table>
                    </TableContainer>
                </Flex>
                
            </Box>
        </Stack>
        </Box>
        )
    }

export default ProjectViewMode