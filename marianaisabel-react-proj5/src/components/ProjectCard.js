import React from "react";
import { useState } from "react";
import {  Link, useParams } from "react-router-dom";


import {FormattedMessage} from "react-intl";

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

function  ProjectCard ({projectElem, ...props}){

    const project = {
        id: projectElem.id,
        imageUrl: projectElem.imageUrl,
        title:  projectElem.title,
        description:  projectElem.description,
        keywords:  projectElem.keywords,
        users: projectElem.users,
        news:  projectElem.news,
        createdBy:  projectElem.createdBy,
        createdDate:  projectElem.createdDate,
        lastModifBy:  projectElem.lastModifBy,
        lastModifDate:  projectElem.lastModifDate,
    }
    //let { id } = useParams(project.id);
    const LastModifBySymbol = chakra(BiEraser);
    const CreateBySymbol = chakra(RiNewspaperLine);
    let id = project.id;
    console.log("O id do use params " + project.id);
   // console.log( new Date((project.lastModifDate)));
   const { isOpen, onOpen, onClose } = useDisclosure()
   const [overlay, setOverlay] = useState("")

    return (
        <Box maxW='sm' borderWidth='1px' borderRadius='lg' overflow='hidden' backgroundColor="white" margin={5}> 

            <HStack display='flex' justifyContent="center"  alignItems="center">
            <Image src={project.imageUrl} alt={project.title.slice(0,10)} h='255px' />
            </HStack>
            <Box p='6'>
                <Box display='flex' alignItems='baseline' flexDirection= {['column', 'column', 'column']} >
                    
                    <Box
                        color='gray.500'
                        fontWeight='semibold'
                        letterSpacing='wide'
                        fontSize='xs'
                        textTransform='uppercase'
                        mb='3'
                        alignSelf={"center"}
                        >
                        {/* {project.users.length} <FormattedMessage id={"members"} />   &bull; {project.news.length} <FormattedMessage id={"news"} />   */}
                        <Button size='xs' variant='link' onClick={() => {
                            setOverlay("members") 
                            onOpen()
                        }}> 
                            {project.users.length} <FormattedMessage id={"members"} /> 
                        </Button>  &bull;  
                        <Button size='xs' variant='link' onClick={() => {
                            setOverlay("news") 
                            onOpen()
                        }}> 
                            {project.news.length} <FormattedMessage id={"news"} />  
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
                                project.users.map(u => (
                                    <ListItem borderRadius='full' px='2' key={u.id} ><Link to={`/profile/${u.id}`} >{u.firstName}</Link></ListItem>
                                ))
                                :  project.news.map(t => (
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
                    {project.keywords.map(n => (
                        <Badge borderRadius='full' px='2' colorScheme='teal' key={n} >{n}</Badge>
                    ))}
                    </HStack>
                </Box>

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
                ? <><LastModifBySymbol color="gray.500" /><Text  as='i' fontSize='sm' ml={1}> <FormattedMessage id={"update_by"} />  {project.lastModifBy}, <FormattedMessage id={"date"} values={{d:  new Date(project.lastModifDate)}} />   </Text> </>
                : <><CreateBySymbol color="gray.500" /> <Text  as='i' fontSize='sm' ml={1} ><FormattedMessage id={"create_by"} />  {project.createdBy}, <FormattedMessage id={"date"} values={{d:  new Date(project.createdDate)}} />  </Text> </>
                }
                </Box>
            </Box>
        </Box>





    )
    
    }
    export default ProjectCard;

    //project.lastModifDate