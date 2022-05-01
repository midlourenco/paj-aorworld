import React from "react";
import { useState , useEffect} from "react";
import {  Link, useParams } from "react-router-dom";

import { useForm } from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";
import {
    Flex,
    Button,
    VStack,
    chakra,
    Link as ChakraLink,
    Box,
    Image ,
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
// import {isImage} from "../auxiliar"

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";




function  NewsArticleCard ({news, ...props}){
    const intl = useIntl();
    const {register, handleSubmit, watch, formState: {errors}}= useForm();
    //let { id } = useParams(news.id);
    const LastModifBySymbol = chakra(BiEraser);
    const CreateBySymbol = chakra(RiNewspaperLine);
    let id = news.id;
    console.log("O id do use params " + news.id);
   // console.log( new Date((news.lastModifDate)));

    const { isOpen, onOpen, onClose } = useDisclosure()
    const [overlay, setOverlay] = useState("")

    const [textColor, setTextColor]=useState("black");

    
    useEffect(()=>{
        if(news.deleted){
            setTextColor("gray")
        }else{
            setTextColor("black")
        }
    },[])

    return (
        <Box  borderWidth='1px' borderRadius='lg' backgroundColor="white" margin={5}> 
            <Flex display='flex' justifyContent="space-between"  alignItems="center" >
            {/* <GridItem colSpan={1}> */}
                <Square size={["100px","250px","250px"]} >            
                    <VStack display='flex' justifyContent="right"  alignItems="right"  >
                    {/* {console.log(isImage(news.image))} */}
                    {news.deleted?
                    (<Box>
                    <Image
                        style={{opacity: 0.2}}
                        src={news.image}
                        fallbackSrc="images/logo.png"
                        h={["100px","250px","250px"]} 
                        alt={news.title.slice(0,10)} 
                    />
                    {/* <Image src={isImage(news.image)} alt={news.title.slice(0,10)} h={["100px","250px","250px"]}  /> */}
                    {/* <Image style={{opacity: 0.2}}
                        alt={news.title.slice(0,10)} 
                        h={["100px","250px","250px"]} 
                        src={ watch === undefined || watch.length === 0
                            ? 'https://upload.wikimedia.org/wikipedia/commons/7/78/Image.jpg'
                            : news.image
                        }                       
                    /> */}
                    </Box>)
                    : <Image
                            src={news.image}
                            fallbackSrc="images/logo.png"
                            h={["100px","250px","250px"]} 
                            alt={news.title.slice(0,10)} 
                        />
                    }
                    
                    </VStack>
                </Square>

                {/* </GridItem>
                <GridItem colSpan={2}> */}
                
                <Box p='6' >
                {news.deleted?
                <Badge colorScheme='red'><FormattedMessage id={"deleted"} /> </Badge>
                :null
                }
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
                            {news.visibility?
                            <Badge borderRadius='full' px='2' color='teal' > {intl.formatMessage({id: 'public'})  }   </Badge>
                            : <Badge borderRadius='full' px='2' color='green' > {intl.formatMessage({id: 'private'})  }   </Badge>
                            }
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
                                    news.users.map(u => (
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
                        {news.deleted?
                            news.keywords.map(n => (
                            <Badge borderRadius='full' px='2' color='grey' key={n} >{n}</Badge>
                            ))
                        : news.keywords.map(n => (
                            <Badge borderRadius='full' px='2' colorScheme='teal' key={n} >{n}</Badge>
                            ))
                        }
                        </HStack>
                    
                    </Box>
                
                    <Box textAlign={"center" } color={textColor}>
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
                        <Text textAlign={"center" }  fontSize="md" color="gray.500" fontWeight="400" mb="30px">
                            {news.description}
                            </Text>
                        </Box>

                    
                        <Box display='flex' flexDirection={"row"} justifyContent="center" mt='6' alignItems='center' >
                        {news.lastModifBy  
                        ? <><LastModifBySymbol color="gray.500" /><Text  as='i' fontSize='sm' ml={1}> <FormattedMessage id={"update_by"} />  {news.lastModifBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(news.lastModifDate)}} />   </Text> </>
                        : <><CreateBySymbol color="gray.500" /> <Text  as='i' fontSize='sm' ml={1} ><FormattedMessage id={"create_by"} />  {news.createdBy.firstName}, <FormattedMessage id={"date"} values={{d:  new Date(news.createdDate)}} />  </Text> </>
                        }
                      
                        </Box>
                    </Box>
                </Box>
                <Box>

                </Box>
            {/* </GridItem> */}
            </Flex>
        </Box>





    )
    
    }
    export default NewsArticleCard;
