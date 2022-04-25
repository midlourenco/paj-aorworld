
import React from "react";
import {
  Link,
  NavLink,
  useParams,
  useLocation,
  Outlet,
  useSearchParams
} from "react-router-dom";

import { useState } from "react";
//https://react-hook-form.com/
import { useForm } from "react-hook-form";
import { FormattedMessage,useIntl} from "react-intl";

import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    VStack,
    StackDivider,
    InputLeftElement,
    chakra,
    Grid,
    GridItem,
    Box,
    Image,
    Badge,
    Tag,
    TagLeftIcon,
    TagLabel,
    Text,
    Avatar,
    FormControl,
    FormHelperText,
    FormErrorMessage,
    InputRightElement,
    HStack,
    Spacer
} from "@chakra-ui/react";
import{SearchIcon , ArrowForwardIcon, AddIcon} from '@chakra-ui/icons';

import NewsArticleCard from "../components/NewsArticleCard";
//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import {FaSearch } from "react-icons/fa";
import {MdOutlineManageSearch , MdOutlineClose} from "react-icons/md";

// function QueryNavLink({ to, ...props }) {
//     let location = useLocation();
//     return <NavLink to={to + location.search} {...props} />;
// }

function  News (){
    //let [searchParams, setSearchParams] = useSearchParams({ replace: true });
    let [searchParams, setSearchParams] = useState("");
    let [selectedKeyword, setSelectedKeyword] = useState("");


    const { id } = useParams();
    const queryString = new URLSearchParams(useLocation().search);
    console.log(queryString.get("id"))

    const SearchSymbol = chakra(FaSearch);
    const ClearSearchSymbol = chakra(MdOutlineClose);
    const keywordsArray = ["java","ReactJS","Swing", "Rest",'Objectos'];
    

    const handleSearchClick = (keyword)=>{
        console.log( keyword);
        setSelectedKeyword(keyword);
    }
    const handleClearSearchClick=()=>{
        setSelectedKeyword("");
    }
    
    const intl = useIntl();


    const n1 = {
        id:'1',
        imageUrl: 'https://rockcontent.com/br/wp-content/uploads/sites/2/2020/02/projeto-pessoal.png',
        title: 'AoR - Lista de Atividades',
        description: 'Projecto PAJ - aplicação web desenvolvido em Java, com uma API REST, persistência de dados em base de dados em mySQL e frontend em ReactJS, javaScript, CSS e HTML.',
        keywords: ['Java', 'ReactJS', 'Rest'],
        users: ['Ricardo', 'Mariana', 'Tiago', 'Inês', 'Anna', 'Mariana'],
        projects: ['HTML/CSS', 'REST',  'mySQL','Websockets'],
        createdBy: 'Mariana',
        createdDate: '2022-04-15',
        lastModifBy: 'Isabel',
        lastModifDate: '2022-04-21'
    }


    const n2 = {
        id:'2',
        imageUrl: 'data:image/jpeg;base64,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',
        title: 'AoR Words',
        description: 'Projecto IPJ - aplicação desenvolvida em Java, com persistência de dados em ficheiros de objectos e frontend em Swing',
        keywords: ['Java', 'Objects', 'Swing'],
        users: ['Mariana', 'Goncalo'],
        projects: ['Java', 'Programar',  'Programar','React'],
        createdBy: 'Mariana',
        createdDate: '2022-04-15',
        lastModifBy: '',
        lastModifDate: ''
    }

    //
    return (
        <Stack spacing={10} backgroundColor="gray.200" pb={20}>
            <Heading as='h1' size='3xl'  ><FormattedMessage id={"news"} /> </Heading>

            <Flex  flexDirection="column" justifyContent="center" alignItems="start"  mr={5}>
                <Flex display="Flex" justifyContent="space-between" width={"100%"}>
                    <InputGroup mx={5}>
                        <InputLeftElement> <SearchSymbol /></InputLeftElement>
                        <Input type="text" 
                            placeholder={intl.formatMessage({id: 'search_keyword'})}
                            width={["90%","75%","45%"]}
                            value={searchParams || ''}
                            _placeholder={{ color: 'teal' }}
                            onChange={(event) => {
                                let filter = event.target.value;
                                if (filter) {
                                //setSearchParams({ filter }, { replace: true });
                                setSearchParams(filter);
                                } else {
                                //setSearchParams({}, { replace: true });
                                setSearchParams("");
                                }
                            }}
                        />
                    </InputGroup>
                    <Button 
                        leftIcon={<AddIcon />} 
                        colorScheme='teal' 
                        variant='outline' 
                        px={[12,8,6]}
                        mr={5}
                    >
                        <Link to="/projects/new"><FormattedMessage id="new_news"></FormattedMessage></Link>
                    </Button>
                </Flex>

                <Stack  color="teal" textDecor={"teal"}  direction={['column', 'row']} spacing='24px' mx={5} my={5}>
        
                    { keywordsArray.filter((keyword)=>{
                            let filter = searchParams;
                            if(!filter) return true;
                            let name = keyword.toLowerCase();
                            return name.startsWith(filter.toLowerCase());

                    }).map((k)=>{
                        
                        return   selectedKeyword && selectedKeyword==k?
                                    <Tag key={k} background="teal.400" color={"white"}>
                                    <TagLeftIcon boxSize='12px' as={SearchIcon} />
                                    <TagLabel > <Link  to="#" key={k} onClick={()=>handleSearchClick(k)} >{k} </Link></TagLabel>
                                    </Tag>
                                    : <Tag key={k} >
                                    <TagLeftIcon boxSize='12px' as={SearchIcon} />
                                    <TagLabel > <Link  to="#" key={k} onClick={()=>handleSearchClick(k)} >{k} </Link></TagLabel>
                                    </Tag>
                                
                        }
                    )}
                </Stack >
                {selectedKeyword && selectedKeyword!=""?
                <Button leftIcon={<ClearSearchSymbol color={"red"}/> } variant='outline' colorScheme='red' size='sm' ml={5} onClick={handleClearSearchClick}><FormattedMessage id={"clear_search"} /></Button>
                : null
                }
            </Flex>
            <VStack
            divider={<StackDivider borderColor='teal.400' variant="dashed" />}
            spacing={4}
            align='stretch'
            width={"100%"}
            >
                <NewsArticleCard newsElem={n1}/>
                <NewsArticleCard newsElem={n2}/>

                <NewsArticleCard newsElem={n2}/>
                <NewsArticleCard newsElem={n2}/>
                <NewsArticleCard newsElem={n2}/>
                <NewsArticleCard newsElem={n2}/>
                <NewsArticleCard newsElem={n2}/>

                <NewsArticleCard newsElem={n1}/>
                <NewsArticleCard newsElem={n1}/>
                <NewsArticleCard newsElem={n1}/>
                <NewsArticleCard newsElem={n1}/>
            </VStack>
        </Stack>
    )
    
    }



    // return (
    //     <div    style={{
    //         height:'100vh',
    //         width: '100%',
    //         background: 'green',
    //         display: 'flex',
    //         justifyContent: 'center',
    //         alignItems: 'center',
    //         fontSize: '1.5rem',
    //         fontWeight:'bold',
    
    //     }}
    //     >
    //        News
    //     </div>
    // )
    
    
    export default News;