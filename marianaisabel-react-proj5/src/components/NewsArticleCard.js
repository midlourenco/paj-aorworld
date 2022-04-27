import React from "react";
import { useState } from "react";
import {  Link, useParams } from "react-router-dom";


import {FormattedMessage} from "react-intl";

import {
    Flex,
    Button,
    VStack,
    chakra,
    Link as ChakraLink,
    Box,
    Image,
    Square,
    Badge,
    Text,
    HStack,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    useDisclosure,
    UnorderedList,
    ListItem
} from "@chakra-ui/react";

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";





function  NewsArticleCard ({news, ...props}){

    //let { id } = useParams(news.id);
    const LastModifBySymbol = chakra(BiEraser);
    const CreateBySymbol = chakra(RiNewspaperLine);
    let id = news.id;
    console.log("O id do use params " + news.id);
   // console.log( new Date((news.lastModifDate)));

    const { isOpen, onOpen, onClose } = useDisclosure()
    const [overlay, setOverlay] = useState("")



    return (
        <Box  borderWidth='1px' borderRadius='lg' backgroundColor="white" margin={5}> 
            <Flex display='flex' justifyContent="center"  alignItems="center" >
            {/* <GridItem colSpan={1}> */}
            <Square size={["100px","250px","250px"]} >            
                <VStack display='flex' justifyContent="center"  alignItems="center"  >
                    <Image src={news.image} alt={news.title.slice(0,10)} h={["100px","250px","250px"]} />
                </VStack>
            </Square>

            {/* </GridItem>
            <GridItem colSpan={2}> */}
           
            <Box p='6' >
                <Box display='flex' alignItems='baseline' flexDirection= {['column', 'column', 'column']} >
                    
                    <Box
                        color='gray.500'
                        fontWeight='semibold'
                        letterSpacing='wide'
                        fontSize='xs'
                        textTransform='uppercase'
                        mb='3'
                        mx='3'
                        alignSelf={"center"}
                        >
                        <Button size='xs' variant='link' onClick={() => {
                            setOverlay("members") 
                            onOpen()
                        }}> 
                            {news.users.length} <FormattedMessage id={"members"} /> 
                        </Button>  &bull;  
                        <Button size='xs' variant='link' onClick={() => {
                            setOverlay("projects") 
                            onOpen()
                        }}> 
                            {news.projects.length} <FormattedMessage id={"projects"} />  
                        </Button>
                        <>
                            <Modal onClose={onClose} isOpen={isOpen} isCentered>
                            <ModalOverlay />
                            <ModalContent>
                            {overlay=="members" ?
                                <ModalHeader>Utilizadores Associados</ModalHeader>
                                :<ModalHeader>Projectos Associados</ModalHeader>
                            }
                                <ModalCloseButton />
                                <ModalBody>
                                <UnorderedList>
                                {overlay=="members" ?
                                news.userss.map(u => (
                                    <ListItem borderRadius='full' px='2' key={u.id} ><Link to={`/profile/${u.id}`} >{u.firstName}</Link></ListItem>
                                ))
                                :  news.projects.map(t => (
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
                    {news.keywords.map(n => (
                        <Badge borderRadius='full' px='2' colorScheme='teal' key={n} >{n}</Badge>
                    ))}
                    </HStack>
                </Box>

                <ChakraLink as={Link} to ={`/news/${id}`}  >
                    <Text fontSize="md"  mt='1'
                    fontWeight='semibold'
                    as='h4'
                    lineHeight='tight'
                    mb='1'
                    
                    > 
                        {news.title} 
                    </Text>
                </ChakraLink>

                <Box  >
                    {news.description}
                </Box>

                <Box display='flex' flexDirection={"row"} justifyContent="center" mt='6' alignItems='center' >
                {news.lastModifBy && news.lastModifBy!="" 
                ? <><LastModifBySymbol color="gray.500" /><Text  as='i' fontSize='sm' ml={1}> <FormattedMessage id={"update_by"} />  {news.lastModifBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(news.lastModifDate)}} />   </Text> </>
                : <><CreateBySymbol color="gray.500" /> <Text  as='i' fontSize='sm' ml={1} ><FormattedMessage id={"create_by"} />  {news.createdBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(news.createdDate)}} />  </Text> </>
                }
                </Box>
            </Box>
            
            {/* </GridItem> */}
            </Flex>
        </Box>





    )
    
    }
    export default NewsArticleCard;
