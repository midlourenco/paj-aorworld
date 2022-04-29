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
import { ExternalLinkIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"

const NewsViewMode=({isAdmin, currentNews,editMode,handleEditClick,handleCancelClick, handleDeleteClick})=>{
    const intl = useIntl();
    const navigate = useNavigate();
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
                {console.log(currentNews)}
                    {/* <Image boxSize='200px' name={currentNews.title} src={currentNews.image} ></Image>            
                   */}
                    {currentNews.deleted?
                (<Box>
                <Badge colorScheme='red'><FormattedMessage id={"deleted"} /> </Badge>
                <Image boxSize='200px' name={currentNews.title} src={currentNews.image} style={{opacity: 0.2}} ></Image> 
                </Box>)
                :  <Image boxSize='200px' name={currentNews.title} src={currentNews.image} ></Image> 
                }

                    {/* <Badge mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentNews.privileges || "-"} defaultMessage={"-"} /></Badge> */}
                </Box>
                <Box mb={7}>
                    <Text fontSize='2xl' my="30px" me="10px" fontWeight={"bold"}> {currentNews.title }</Text>
                    <Text fontSize="md" color="gray.500" fontWeight="400" mb="30px" > {currentNews.description }</Text>
                    <Text fontSize="md" fontWeight="bold" me="10px"> <FormattedMessage id={"create_by"} /> <Text  as='i' fontSize='sm' ml={1} ontSize="md" color="gray.500" fontWeight="400" mb="30px"> {currentNews.createdBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(currentNews.createdDate)}} />  </Text> </Text>
                    {currentNews.lastModifDate!=="" && currentNews.lastModifDate!=null ?
                    <Text fontSize="md" fontWeight="bold" me="10px"> <FormattedMessage id={"update_by"} /> <Text  as='i' fontSize='sm' ml={1} ontSize="md" color="gray.500" fontWeight="400" mb="30px">  {currentNews.lastModifBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(currentNews.lastModifDate)}} />   </Text>     </Text>                         
                    :null
                    }
                </Box>
                
                
                <Heading mt={10} as="h3" ><FormattedMessage id={"associated_projects"} />:</Heading>

                <TableContainer>
                <Table >
                    <Thead>
                    <Tr>
                        <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
                        <Th>{intl.formatMessage({id: 'create_by'})}</Th>
                        <Th><FormattedMessage id={"form_field_create_date"}/></Th>

                    </Tr>
                    </Thead>
                    <Tbody >
                    {currentNews.projects.map((p)=>(
                        <Tr key={p.id}>
                        <Td color="gray.500" fontWeight="400"  >{p.title}</Td>
                        <Td color="gray.500" fontWeight="400" >{p.createdBy.firstName}</Td>
                        <Td color="gray.500" fontWeight="400"  textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(p.createdDate)}} /> </Td>
                        <IconButton onClick={()=> navigate("/projects/"+p.id)} aria-label={intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} />

                    </Tr>
                    ))}  
                    </Tbody>
                </Table>
                </TableContainer>
            

            <Heading mt={10} as="h3" ><FormattedMessage id={"associated_users"} />:</Heading>

            <TableContainer>
            <Table >
                <Thead>
                <Tr>
                    <Th>{intl.formatMessage({id: 'user_name'})}</Th>
                    <Th>{intl.formatMessage({id: 'form_field_email'})}</Th>
                </Tr>
                </Thead>
                <Tbody>
                {currentNews.users.map((u)=>(
                    <Tr key={u.id}>
                    <Td color="gray.500" fontWeight="400" >{u.firstName + " " + u.lastName}</Td>
                    <Td color="gray.500" fontWeight="400" >{u.email}</Td>
                    <IconButton onClick={()=> navigate("/users/"+u.id)} aria-label={intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} />

                </Tr>
                ))}  
                </Tbody>
            </Table>
            </TableContainer>

            {!currentNews.deleted?
                    (<Box >
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    <EditableControls  isAdmin={isAdmin} editMode={editMode}  handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} handleCancelClick={handleCancelClick} />
                    </Box>)
                    :null
                    //TODO: adicionar botao de recuperar
                }
            </Flex>
            
        </Box>
    </Stack>
    </Box>
    )
}


export default NewsViewMode