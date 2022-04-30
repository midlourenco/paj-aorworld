import React, { useEffect } from "react";
import { useState } from "react";
import {  Link, useParams } from "react-router-dom";
import {FormattedMessage ,useIntl} from "react-intl";
import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    InputLeftElement,
    chakra,
    Grid,
    Link as ChakraLink,
    Box,
    Image,
    Badge,
    Text,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    InputRightElement,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    useDisclosure,
    UnorderedList,
    ListItem,
    HStack,
    Spacer
} from "@chakra-ui/react";

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";

function  ProjectCard ({project, ...props}){
    const intl = useIntl();
    // const project = {
    //     id: projectElem.id,
    //     image: projectElem.image,
    //     title:  projectElem.title,
    //     description:  projectElem.description,
    //     keywords:  projectElem.keywords,
    //     associatedUsers: projectElem.associatedUsers,
    //     associatedNews:  projectElem.associatedNews,
    //     createdBy:  projectElem.createdBy,
    //     createdDate:  projectElem.createdDate,
    //     lastModifBy:  projectElem.lastModifBy,
    //     lastModifDate:  projectElem.lastModifDate,
    //     visibility:  projectElem.visibility,
    //     deleted:  projectElem.deleted,
    // }
    //let { id } = useParams(project.id);
    const LastModifBySymbol = chakra(BiEraser);
    const CreateBySymbol = chakra(RiNewspaperLine);
    let id = project.id;
    console.log("O id do use params " + project.id);
   // console.log( new Date((project.lastModifDate)));
   const { isOpen, onOpen, onClose } = useDisclosure()
   const [overlay, setOverlay] = useState("")
    const [textColor, setTextColor]=useState("black");

    useEffect(()=>{
        if(project.deleted){
            setTextColor("gray")
        }else{
            setTextColor("black")
        }
    },[])


    return (
        <Box maxW='sm' borderWidth='1px' borderRadius='lg' overflow='hidden' backgroundColor="white" margin={5}> 

            <HStack display='flex' justifyContent="center"  alignItems="center">
           
            {project.deleted?
            (<Box>
            <Badge colorScheme='red'><FormattedMessage id={"deleted"} /> </Badge>
            <Image 
            src={project.image} 
            fallbackSrc="images/logo.png"
            alt="project_image" 
            h='255px' 
            style={{opacity: 0.2}} 
            />
            </Box>)
            : <Image src={project.image} alt="project_image" h='255px' fallbackSrc="images/logo.png"  />
            }
            </HStack>
            
            <Box p='6'>
                <Box display='flex' alignItems='baseline' flexDirection= {['column', 'column', 'column']} >
                {/* <Badge colorScheme='red'><FormattedMessage id={"deleted"} />  */}
                    <Box
                        color='gray.500'
                        fontWeight='semibold'
                        letterSpacing='wide'
                        fontSize='xs'
                        textTransform='uppercase'
                        mb='3'
                        alignSelf={"center"}
                        >
                            {project.visibility?
                            <Badge borderRadius='full' px='2' color='teal' > {intl.formatMessage({id: 'public'})  }   </Badge>
                            : <Badge borderRadius='full' px='2' color='green' > {intl.formatMessage({id: 'private'})  }   </Badge>
                            }
                        {/* {project.associatedUsers.length} <FormattedMessage id={"members"} />   &bull; {project.associatedNews.length} <FormattedMessage id={"associatedNews"} />   */}
                        <Button size='xs' variant='link' onClick={() => {
                            setOverlay("members") 
                            onOpen()
                        }}> 
                            {project.associatedUsers.length} <FormattedMessage id={"members"} /> 
                        </Button>  &bull;  
                        <Button size='xs' variant='link' onClick={() => {
                            setOverlay("associatedNews") 
                            onOpen()
                        }}> 
                            {project.associatedNews.length} <FormattedMessage id={"associatedNews"} />  
                        </Button>
                        <>
                            <Modal onClose={onClose} isOpen={isOpen} isCentered>
                            <ModalOverlay />
                            <ModalContent>
                            {overlay=="members" ?
                                <ModalHeader>Utilizadores Associados</ModalHeader>
                                :<ModalHeader>Notícias Associadass</ModalHeader>
                            }
                                <ModalCloseButton />
                                <ModalBody>
                                <UnorderedList>
                                {overlay=="members" ?
                                project.associatedUsers.map(u => (
                                    <ListItem borderRadius='full' px='2' key={u.id} ><Link to={`/profile/${u.id}`} >{u.firstName}</Link></ListItem>
                                ))
                                :  project.associatedNews.map(t => (
                                    <ListItem borderRadius='full' px='2' key={t.id} ><Link to={`/projects/${t.id}`} >{t.title}</Link></ListItem>
                                ))
                                }
                                </UnorderedList>
                                </ModalBody>
                                <ModalFooter>
                                <Button onClick={onClose}>Close</Button>
                                </ModalFooter>
                            </ModalContent>
                            </Modal>
                        </>
                    
                    </Box>
                    
                    <HStack  mb='5' alignSelf={"center"}>
                    {project.deleted?
                        project.keywords.map(k => (
                        <Badge borderRadius='full' px='2' color='grey' key={k} >{k}</Badge>
                        ))
                    : project.keywords.map(k => (
                        <Badge borderRadius='full' px='2' colorScheme='teal' key={k} >{k}</Badge>
                        ))
                    }
                    </HStack>
                {/* </Badge> */}
                </Box>

                <Box color={textColor}>
                <ChakraLink as={Link} to ={`/projects/${id}`}  >
                    <Text fontSize="md"  mt='1'
                    fontWeight='semibold'
                    as='h4'
                    lineHeight='tight'
                    mb='1'
                    isTruncated
                    > 
                        {project.title} 
                    </Text>
                </ChakraLink>

                <Box  isTruncated>
                    {project.description}
                </Box>

                <Box display='flex' flexDirection={"row"} justifyContent="right" mt='6' alignItems='center' >
                {project.lastModifBy && project.lastModifBy!="" 
                ? <><LastModifBySymbol color="gray.500" /><Text  as='i' fontSize='sm' ml={1}> <FormattedMessage id={"update_by"} />  {project.lastModifBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(project.lastModifDate)}} />   </Text> </>
                : <><CreateBySymbol color="gray.500" /> <Text  as='i' fontSize='sm' ml={1} ><FormattedMessage id={"create_by"} />  {project.createdBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(project.createdDate)}} />  </Text> </>
                }
                </Box>
                </Box>
            </Box>
        </Box>





    )
    
    }
    export default ProjectCard;

    //project.lastModifDate