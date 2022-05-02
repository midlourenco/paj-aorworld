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
    Tabs, TabList, TabPanels, Tab, TabPanel,
    VStack,
} from "@chakra-ui/react";
import { CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"
import { FaUserTimes,  FaUserCheck} from 'react-icons/fa';
import MyProjects from './MyProjects';
import AssocProjects from './AssocProjects';
import DeletedProjects from './DeletedProjects';
import MyNews from './MyNews';
import AssocNews from './AssocNews';
import DeletedNews from './DeletedNews';
import UserDetails from './UserDetails';

//TODO: 
function setAppError(error){
    console.log(error)
}

const ProfileViewMode=({errorTopBar="",idToResquest,canEdit,isAdmin, userId, currentUser,editMode,handleEditClick,handleCancelClick, handleDeleteClick, ...props})=>{
    const intl = useIntl();
    const navigate = useNavigate();
    const { get, post, del, response, loading, error } = useFetch();
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
 
// useEffect(()=>{
//     window.scrollTo(0, 0)
// },[])




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
                <UserDetails currentUser={currentUser} />
               {canEdit&& <Box>
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    <EditableControls isAdmin={isAdmin} editMode={editMode}  handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} handleCancelClick={handleCancelClick} />
                </Box>}

                <Box>
                <Tabs variant='soft-rounded' colorScheme='green' mt={20}>
                    <TabList>
                        <Tab>All</Tab>
                        <Tab>Own projects</Tab>
                        <Tab>Associated projects</Tab>
                        <Tab>Deleted projects</Tab>
                        <Tab>Own news</Tab>
                        <Tab>Associated news</Tab>
                        <Tab>Deleted news</Tab>

                    </TabList>
                        <TabPanels>
                            <TabPanel>
                                <VStack spacing={10}>
                                    <MyProjects idToResquest={idToResquest} />
                                    <AssocProjects idToResquest={idToResquest}/>
                                    <DeletedProjects idToResquest={idToResquest}/>
                                    <MyNews idToResquest={idToResquest} />
                                    <AssocNews  idToResquest={idToResquest}/>
                                    <DeletedNews idToResquest={idToResquest} />
                                </VStack>
                            </TabPanel>
                            <TabPanel>
                                <VStack spacing={10}>
                                    <MyProjects />
                                </VStack>
                            </TabPanel>
                            <TabPanel>
                            <VStack spacing={10}>
                                <AssocProjects />
                            </VStack>
                            </TabPanel>
                            <TabPanel>
                            <VStack spacing={10}>
                                <DeletedProjects />
                            </VStack>
                            </TabPanel>
                            <TabPanel>
                            <VStack spacing={10}>
                                <MyNews />
                            </VStack>
                            </TabPanel>
                            <TabPanel>
                            <VStack spacing={10}>
                                <AssocNews />
                            </VStack>
                            </TabPanel>
                            <TabPanel>
                            <VStack spacing={10}>
                                <DeletedNews />
                            </VStack>
                            </TabPanel>
                        </TabPanels>
                    </Tabs>
                    </Box>


                    {/* <VStack spacing={10}>
                        <MyProjects />
                        <AssocProjects />
                        <DeletedProjects />
                        <MyNews />
                        <AssocNews />
                        <DeletedNews />
                    </VStack> */}
                </Flex>
        </Box>
    </Stack>
    </Box>
    )
}
const mapStateToProps = state => {
    return {userPriv: state.loginOK.userPriv,
            userId: state.loginOK.userId,
            errorTopBar: state.errorMsg.errorTopBar,
    };
  };
  
  
  export default  connect(mapStateToProps, {})(ProfileViewMode)


