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
    Stack,
    Spinner,
    Box,
    Link,
    Select,
    Avatar,
    Badge,
    Image,
    Text,
    Spacer,
    Textarea,
    Tooltip,
    IconButton,
    Table,
    Thead,
    Tbody,
    HStack,
    Tfoot,
    Tr,
    Th,
    Td,
    TableCaption,
    TableContainer,
    Button,
    useToast,
} from "@chakra-ui/react";
import { ExternalLinkIcon,LinkIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import EditableControls from "../../EditableControls"

//TODO: 
function setAppError(error){
    console.log(error)
}

const NewsViewMode=({canEdit,isAdmin, canAssocMyself,setUserInCurrentNews ,currentNews,editMode,handleEditClick,handleCancelClick, handleDeleteClick,...props})=>{
    const intl = useIntl();
    const navigate = useNavigate();
    const toast = useToast()
    const { get, post, del, response, loading, error } = useFetch();
    
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    // const [userInCurrentNews, setUserInCurrentNews ]= useState(currentNews.users);
  
    const handleAssociateMeClick=async()=>{
        const assocMeToNews = await post("news/"+currentNews.id+"/associateToMySelf")
        if (response.ok) {
            console.log(assocMeToNews)
            toast({
                title: 'User associated with success',
                description: "You was  associated to the news " + currentNews.id,
                status: 'success',
                duration: 5000,
                isClosable: true,
                })
                setUserInCurrentNews(currentNews.id) // eu nao tenho o objecto da minha pessoa por isso para se o ir buscar tem mesmo que se for??ar refender ?? pagina que faz o get das news
                setRestResponse("OK");
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setRestResponse("NOK");
            setAppError('error_fetch_login_401');
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
    }

    if(!currentNews || loading ){
        
        return (<>
           
            <Spinner />
            </>
        )
        }
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
                
                <Image style={{opacity: 0.2}} mt={10} name="image2" size={"xs"} src={ currentNews.image} fallbackSrc="../images/logo.png"  alt="project_image"/>
       
                </Box>)
                :      <Image mt={10} name="image2" size={"xs"} src={ currentNews.image} fallbackSrc="../images/logo.png"  alt="project_image"/>
       
                }

                    {/* <Badge mx={2} fontSize={"10px"} color={"teal.400"} ><FormattedMessage id={currentNews.privileges || "-"} defaultMessage={"-"} /></Badge> */}
                </Box>
                <Box   textAlign={"center"} mb={7}>
                    <Text fontSize='2xl' my="30px" me="10px" fontWeight={"bold"}> {currentNews.title }</Text>
                    <Text fontSize="md" color="gray.500" fontWeight="400" mb="30px" > {currentNews.description }</Text>
                   <Box>
                    {currentNews.deleted?
                        currentNews.keywords.map(k => (
                        <Badge borderRadius='full' px='2' color='grey' key={k} >{k}</Badge>
                        ))
                    : currentNews.keywords.map(k => (
                        <Badge borderRadius='full' px='2' colorScheme='teal' key={k} >{k}</Badge>
                        ))
                    }
                </Box>

                    {currentNews.visibility?
                        <Badge borderRadius='full' px='2' color='teal' > {intl.formatMessage({id: 'public'})  }   </Badge>
                        : <Badge borderRadius='full' px='2' color='green' > {intl.formatMessage({id: 'private'})  }   </Badge>
                    }

                    <Text fontSize="md" fontWeight="bold" me="10px"> <FormattedMessage id={"create_by"} /> <Text  as='i' fontSize='sm' ml={1}  color="gray.500" fontWeight="400" mb="30px"> {currentNews.createdBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(currentNews.createdDate)}} />  </Text> </Text>
                    {currentNews.lastModifDate!=="" && currentNews.lastModifDate!=null ?
                    <Text fontSize="md" fontWeight="bold" me="10px"> <FormattedMessage id={"update_by"} /> <Text  as='i' fontSize='sm' ml={1} color="gray.500" fontWeight="400" mb="30px">  {currentNews.lastModifBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(currentNews.lastModifDate)}} />   </Text>     </Text>                         
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
                        <Td>
                        <Tooltip label ={ "/projects/"+ p.id} >
                        <IconButton onClick={()=> navigate("/projects/"+p.id)} aria-label={intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} />
                        </Tooltip>
                        </Td>
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
                    <Td>
                    <Tooltip label ={ "/profile/"+ u.id} >
                    <IconButton onClick={()=> navigate("/profile/"+u.id)} aria-label={intl.formatMessage({id: 'go_to'})} icon={<ExternalLinkIcon />} />
                    </Tooltip>
                    </Td>
                </Tr>
                ))}  
                </Tbody>
            </Table>
            </TableContainer>


            {canAssocMyself && <Button 
            onClick={handleAssociateMeClick} 
            mt={6} 
            size='md' 
            rightIcon={<LinkIcon />}  
            colorScheme={"teal"} 
            > 
                <FormattedMessage id={"associate_myself_to_news"} defaultMessage={"-"}/>
            </Button>
        }
            {canEdit?
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