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
import EditableControls from "../../EditableControls"
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';


//TODO: 
function setAppError(error){
    console.log(error)
}

const ProfileViewMode=({isAdmin, userId, currentUser,editMode,handleEditClick,handleCancelClick, handleDeleteClick})=>{
    const intl = useIntl();
    const { get, post, del, response, loading, error } = useFetch();
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    const [projectsCreatedByMe,setProjectsCreatedByMe]=useState([])
    const [projectsAssocToMe,setProjectsAssocToMe]=useState([])
    const [newsAssocToMe,setNewsAssocToMe]=useState([])
    const [newsCreatedByMe,setNewsCreatedByMe]=useState([])
    const [deletedNewsAssocToMe,setDeletedNewsAssocToMe]=useState([])
    const [deletedProjectsAssocToMe,setDeletedProjectsAssocToMe]=useState([])

    useEffect( async() => {
        console.log("houve refresh vou buscar notif " );    
        setRestResponse("");

        const projectsCreatedByMe = await get('/projects?user=' + userId);
        if (response.ok) {
            (console.log("proj criados" +response))
            setProjectsCreatedByMe(projectsCreatedByMe);;
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
        
        const projectsAssocToMe= await get('/projects/projectAssocToUser?user=' + userId);
        if (response.ok) {
            (console.log("proj assoc" +response))
            setProjectsAssocToMe(projectsAssocToMe);
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
        const deletedProjectsAssocToMe= await get('/projects/deletedList?user=' + userId);
        if (response.ok) {
            (console.log("proj assoc" +response))
            setDeletedProjectsAssocToMe(deletedProjectsAssocToMe);
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }

        const newsCreatedByMe = await get('/news?user=' + userId);
        if (response.ok) {
            (console.log("noticias assoc" + response))
            setNewsCreatedByMe(newsCreatedByMe);
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }

        
        const newsAssocToMe = await get('/news/associatedToUser?user=' + userId);
        if (response.ok) {
            (console.log("noticias assoc" + response))
            setNewsAssocToMe(newsAssocToMe);
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
        const deletedNewsAssocToMe = await get('/news/deletedList?user=' + userId);
        if (response.ok) {
            (console.log("noticias assoc" + response))
            setDeletedNewsAssocToMe(deletedNewsAssocToMe);
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_generic');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
            setAppError(  error);
            }else{
                setAppError(  "error_fetch_generic" );
            }
        }
 
    },[])





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
            p={[5,5,10]}
            flexDirection={"column"}
            justifyContent={"space-between"}
            >
                <Heading as="h3" ><FormattedMessage id={"personal_details"} />:</Heading>

                <Box>
                {/* {console.log(currentUser)}                             */}
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
            
            
            <Heading as="h3" ><FormattedMessage id={"my_projects"} />:</Heading>

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
                {projectsCreatedByMe.map((p)=>(
                <Tr key={p.id}>
                    <Td>{p.title}</Td>
                    <Td>{p.createdBy.firstName}</Td>
                    <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(p.createdDate)}} /> </Td>
                    <Td>
                    </Td>
                </Tr>
                ))}  
                </Tbody>
            </Table>
            </TableContainer>




            <Heading as="h3" ><FormattedMessage id={"associated_projects"} />:</Heading>

                    <TableContainer>
                    <Table >
                        <Thead>
                        <Tr>
                            <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
                            <Th>{intl.formatMessage({id: 'create_by'})}</Th>
                            <Th><FormattedMessage id={"form_field_create_date"}/></Th>
                            <Th>{intl.formatMessage({id: 'desassociate'})}</Th>
                        </Tr>
                        </Thead>
                        <Tbody>
                        {projectsAssocToMe.map((p)=>(
                        <Tr key={p.id}>
                            <Td>{p.title}</Td>
                            <Td>{p.createdBy.firstName}</Td>
                            <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(p.createdDate)}} /> </Td>
                            <Td>
                            <ButtonGroup>
                                <Tooltip label={intl.formatMessage({id: 'tooltip_desassociate'})}>
                                    <Button maxW="130px" colorScheme={"teal"} fontSize="sm" leftIcon={<FaUserTimes />} > <FormattedMessage id={"desassociate"}/></Button>
                                </Tooltip>
                            </ButtonGroup>
                            </Td>
                        </Tr>
                        ))}  
                        </Tbody>
                    </Table>
                    </TableContainer>

                    <Heading as="h3" ><FormattedMessage id={"deleted_projects"} />:</Heading>

<TableContainer>
<Table >
    <Thead>
    <Tr>
        <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
        <Th>{intl.formatMessage({id: 'create_by'})}</Th>
        <Th><FormattedMessage id={"form_field_create_date"}/></Th>
        <Th>{intl.formatMessage({id: 'desassociate'})}</Th>
    </Tr>
    </Thead>
    <Tbody>
    {deletedProjectsAssocToMe.map((p)=>(
    <Tr key={p.id}>
        <Td>{p.title}</Td>
        <Td>{p.createdBy.firstName}</Td>
        <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(p.createdDate)}} /> </Td>
        <Td>
        <ButtonGroup>
            <Tooltip label={intl.formatMessage({id: 'tooltip_desassociate'})}>
                <Button maxW="130px" colorScheme={"teal"} fontSize="sm" leftIcon={<FaUserTimes />} > <FormattedMessage id={"desassociate"}/></Button>
            </Tooltip>
        </ButtonGroup>
        </Td>
    </Tr>
    ))}  
    </Tbody>
</Table>
</TableContainer>


    <Heading as="h3" ><FormattedMessage id={"my_news"} />:</Heading>

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
{newsCreatedByMe.map((n)=>(
<Tr key={n.id}>
    <Td>{n.title}</Td>
    <Td>{n.createdBy.firstName}</Td>
    <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(n.createdDate)}} /> </Td>
    
</Tr>
))}  
</Tbody>
</Table>
</TableContainer>

    <Heading as="h3" ><FormattedMessage id={"associated_news"} />:</Heading>

<TableContainer>
<Table >
<Thead>
<Tr>
        <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
        <Th>{intl.formatMessage({id: 'create_by'})}</Th>
        <Th><FormattedMessage id={"form_field_create_date"}/></Th>
        <Th>{intl.formatMessage({id: 'desassociate'})}</Th>
</Tr>
</Thead>
<Tbody>
{newsAssocToMe.map((n)=>(
<Tr key={n.id}>
    <Td>{n.title}</Td>
    <Td>{n.createdBy.firstName}</Td>
    <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(n.createdDate)}} /> </Td>
    <Td>
    <ButtonGroup>
        <Tooltip label={intl.formatMessage({id: 'tooltip_desassociate'})}>
            <Button maxW="130px" colorScheme={"teal"} fontSize="sm" leftIcon={<FaUserTimes />} > <FormattedMessage id={"desassociate"}/></Button>
        </Tooltip>
    </ButtonGroup>
    </Td>
</Tr>
))}  
</Tbody>
</Table>
</TableContainer>

            <Heading as="h3" ><FormattedMessage id={"my_deleted_news"} />:</Heading>

                    <TableContainer>
                    <Table >
                    <Thead>
                    <Tr>
                            <Th>{intl.formatMessage({id: 'form_field_title'})}</Th>
                            <Th>{intl.formatMessage({id: 'create_by'})}</Th>
                            <Th><FormattedMessage id={"form_field_create_date"}/></Th>
                            <Th>{intl.formatMessage({id: 'desassociate'})}</Th>
                    </Tr>
                    </Thead>
                    <Tbody>
                    {deletedNewsAssocToMe.map((n)=>(
                    <Tr key={n.id}>
                        <Td>{n.title}</Td>
                        <Td>{n.createdBy.firstName}</Td>
                        <Td textAlign={"center"}><FormattedMessage id={"only_date"} values={{d:  new Date(n.createdDate)}} /> </Td>
                        <Td>
                        <ButtonGroup>
                            <Tooltip label={intl.formatMessage({id: 'tooltip_desassociate'})}>
                                <Button maxW="130px" colorScheme={"teal"} fontSize="sm" leftIcon={<FaUserTimes />} > <FormattedMessage id={"desassociate"}/></Button>
                            </Tooltip>
                        </ButtonGroup>
                        </Td>
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
const mapStateToProps = state => {
    return {userPriv: state.loginOK.userPriv,
            userId: state.loginOK.userId,
            error: state.errorMsg.error,
    };
  };
  
  
  export default  connect(mapStateToProps, {})(ProfileViewMode)


