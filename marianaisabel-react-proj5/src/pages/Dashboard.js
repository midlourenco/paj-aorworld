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
    Text,
    Box,
    Grid,
    GridItem,
    Stack,
    Spinner,

} from "@chakra-ui/react";
import useFetch from 'use-http';
import { connect } from 'react-redux'
import ErrorMsgTopBar from '../components/ErrorMsgTopBar'
import DashboardCard from "../components/Dashboard/DashboardCard"
import PieChartCustom from "../components/Dashboard/PieChartCustom"
import{connectWSGeneralDashboard} from "../api_websocket"
import NewsArticleCard from "../components/NewsArticleCard"
import ProjectCard from "../components/ProjectCard"

//TODO: 
function setAppError(error){
    console.log(error)
}

//  * @param {*} updatetUsersList corresponde à função da acção que guarda o valor do numero de utilizadores registados


   /**
 * Dashboard é a camada que aloja os nossos outros componentes.
 * Nesta função estamos a estabelecer ligação ao websocket geral, e enviar a informação os nossos componentes por props, através do uso do estado local (useState)
 * Estamos a guardar no reducer a informção sobre o número de utilizadores, pois teremos elementos à escuta de alterações neste valor (lista de utilizadores)
 * @param {*} error corresponde à informação que vem do reducer sobre informação se existe ou não um erro, sobre a qual se faz a logica de exibir ou não o componente
 * @param {*} ...props restantes props que possam ser passadas
* @returns 
 */
function Dashboard({errorTopBar,...props}) {
    //const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    const navigate = useNavigate();


    /**** *******************************************STATE******************************************************** */

    const [isAdmin, setAdminPriv]=useState(false); // is logged user an admin?
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    

    /**** ****************************************FORM*********************************************************** */
    //fuções que chamos ao submeter o formulário de edição
    const {register, handleSubmit, formState: {errors}}= useForm();
    async function onSubmit (data, e) {
    }
     /**** ******************************************USE EFFECT********************************************************* */



    const [totalUsers, setTotalUsers]=useState("--");
    const [totalNews, setTotalNews]=useState("--");
    const [totalProjects, setTotalProjects]=useState("--");
    const [totalKeywords, setTotalKeywords]=useState("--");
    const [newsPieChart, setNewsPieChart] = useState([]);
    const [moreRecentNews, setMoreRecentNews] = useState(null);
    const [stats, setStats] = useState("--");

    console.log(errorTopBar);
    useEffect(()=>{
        const socket = connectWSGeneralDashboard((evt)=>{
            console.log("info do ws do dashboard geral" + evt.data)

            console.log(JSON.parse(evt.data));
            let stats =JSON.parse(evt.data);
            setTotalUsers( stats.TotalUsers);
            setTotalKeywords( stats.TotalKeywords);
            setTotalProjects( stats.TotalProjects);
            setTotalNews( stats.TotalNews);
            setNewsPieChart(stats.newsPieChart);
            setMoreRecentNews(stats.moreRecentNews);
        // setAvgActiv ( parseFloat(stats.AvgActivPerUser).toFixed(2)); 
    
        return ()=>{
            socket.close();
        }

            // updatetUsersList(stats.TotalUsers);

        });
    },[]);
    
//rowSpan={2} -> para ocupar mais do que uma linha ao alto
//colSpan={4} -> para ocupar mais do que uma coluna em linha 

/**** ******************************************RENDER / RETURN PRINCIPAL ********************************************************* */



   if(!newsPieChart ||!moreRecentNews ){
        
    return (<>
        <Text>Loading...</Text>
        <Spinner />
        </>
    )
    } 
    return (
    <Box background={"teal.400"} >
        <Heading  my={0} py={12} color={"white"} ><FormattedMessage id={"dashboard"} /> </Heading>
          <Flex
        flexDirection="column"
        width="100wh"
        minHeight="83vh"
        backgroundColor="gray.200"
        justifyContent="start"
        alignItems="center"
        py={10}
        >

        
  
        {errorTopBar && 'Error!'}
        {errorTopBar && errorTopBar!=""
        ? <ErrorMsgTopBar />
        : null
        }
        <Flex justifyContent={"center"} alignItems={"center"} >
                <Grid
                // h='200px'
                templateRows='repeat(2, 1fr)'
                templateColumns='repeat(2, 1fr)'
                gap={8}
            
                width={"80%"}
                mx={20}
                >
                <GridItem  >
                <Heading>{intl.formatMessage({id: "projects"})}</Heading>
                
                <DashboardCard title={intl.formatMessage({id: 'tt_projects'})} value={totalProjects}/> 
                      {/*TODO:*/ }
                     <PieChartCustom data={newsPieChart}  />
                    <Text>{intl.formatMessage({id: 'more_recente_project'})}:</Text>
                    {/*TODO:*/ }
                    {/* <ProjectCard project ={moreRecentNews} ></ProjectCard> */}
                </GridItem>
                <GridItem >
                    <Heading>{intl.formatMessage({id: "news"})}</Heading>
                    <DashboardCard title={intl.formatMessage({id: 'tt_news'})} value={totalNews}/> 
                    <PieChartCustom data={newsPieChart}  />
                    <Text>{intl.formatMessage({id: 'more_recente_news'})}:</Text>
                    <NewsArticleCard news ={moreRecentNews} ></NewsArticleCard>
                </GridItem>
                <GridItem  >
                    <Heading>{intl.formatMessage({id: "users"})}</Heading>
                    <DashboardCard title={intl.formatMessage({id: 'tt_users'})} value={totalUsers}/> 


                </GridItem>
                <GridItem >
                    <Heading>{intl.formatMessage({id: "keywords"})}</Heading>
                    <DashboardCard title={intl.formatMessage({id: 'tt_keywords'})} value={totalKeywords}/> 
                </GridItem>
                </Grid>
</Flex>
            {/* 
                <Box >
                    <UserFilter />
                </Box>
                <Box >
                    <UserCards /> 
                </Box > 
            </Box> */}
            <Box >
                {/* <Heading>{intl.formatMessage({id: header})}</Heading> */}
                {/* <PieChartCustom data={newsPieChart} header="news" /> */}
                {/* <LineChart usersPerDay={usersPerDay}/> */}
            </Box>
        

        {restResponse=="OK"?
        <Text my={5} color="green"><FormattedMessage id={"sucess_on_updating"} /> </Text>
        : restResponse=="NOK"?
        <Text my={5} color="red"> <FormattedMessage id={"error_on_updating"} />  </Text>
        :null
        }
        {/* <Box mb={30}>
            <RedirectButton path="/about" description={intl.formatMessage({id: "_back_to_team" })} />
        </Box > */}
        {/* <Avatar name={register.firstName & " " & register.lastName} src={register.image} /> */}
        </Flex>
    </Box>
    );
}

const mapStateToProps = state => {
    return { userPriv: state.loginOK.userPriv,
        errorTopBar: state.errorMsg.errorTopBar
    };
};

export default connect(mapStateToProps, {})(Dashboard);

