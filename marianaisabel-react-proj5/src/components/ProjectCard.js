import React from "react";
import {
  BrowserRouter as Router,
  Switch,
  Route,
  Link,
  useParams
} from "react-router-dom";


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
                        {project.users.length} <FormattedMessage id={"members"} />   &bull; {project.news.length} <FormattedMessage id={"news"} />  
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