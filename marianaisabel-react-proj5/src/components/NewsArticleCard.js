import React from "react";
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
    GridItem,
    Link as ChakraLink,
    Box,
    Image,
    Square,
    Badge,
    Text,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    InputRightElement,
    HStack,
    Spacer
} from "@chakra-ui/react";

//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";

function  NewsArticleCard ({newsElem, ...props}){

    const news = {
        id: newsElem.id,
        imageUrl: newsElem.imageUrl,
        title:  newsElem.title,
        description:  newsElem.description,
        keywords:  newsElem.keywords,
        users: newsElem.users,
        projects:  newsElem.projects,
        createdBy:  newsElem.createdBy,
        createdDate:  newsElem.createdDate,
        lastModifBy:  newsElem.lastModifBy,
        lastModifDate:  newsElem.lastModifDate,
    }
    //let { id } = useParams(news.id);
    const LastModifBySymbol = chakra(BiEraser);
    const CreateBySymbol = chakra(RiNewspaperLine);
    let id = news.id;
    console.log("O id do use params " + news.id);
   // console.log( new Date((news.lastModifDate)));
    return (
        <Box maxW='sm' borderWidth='1px' borderRadius='lg' overflow='hidden' backgroundColor="white" margin={5}> 
            <Flex >
            {/* <GridItem colSpan={1}> */}
            <Square size='200px'>            
                <HStack display='flex' justifyContent="center"  alignItems="center">
                <Image src={news.imageUrl} alt={news.title.slice(0,10)} h='255px' />
                </HStack>
            </Square>

            {/* </GridItem>
            <GridItem colSpan={2}> */}
            <Square minWidth={"80%"}>
            <Box p='6'  >
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
                        {news.users.length} <FormattedMessage id={"members"} />   &bull; {news.projects.length} <FormattedMessage id={"projects"} />  
                    </Box>
                    <HStack  mb='5' alignSelf={"center"}>
                    {news.keywords.map(n => (
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
                        {news.title} 
                    </Text>
                </ChakraLink>

                <Box  isTruncated>
                    {news.description}
                </Box>

                <Box display='flex' flexDirection={"row"} justifyContent="right" mt='6' alignItems='center' >
                {news.lastModifBy && news.lastModifBy!="" 
                ? <><LastModifBySymbol color="gray.500" /><Text  as='i' fontSize='sm' ml={1}> <FormattedMessage id={"update_by"} />  {news.lastModifBy}, <FormattedMessage id={"date"} values={{d:  new Date(news.lastModifDate)}} />   </Text> </>
                : <><CreateBySymbol color="gray.500" /> <Text  as='i' fontSize='sm' ml={1} ><FormattedMessage id={"create_by"} />  {news.createdBy}, <FormattedMessage id={"date"} values={{d:  new Date(news.createdDate)}} />  </Text> </>
                }
                </Box>
            </Box>
            </Square>
            {/* </GridItem> */}
            </Flex>
        </Box>





    )
    
    }
    export default NewsArticleCard;
