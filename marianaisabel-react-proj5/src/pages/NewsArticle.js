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
    Spinner,
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
    InputRightElement
} from "@chakra-ui/react";
import { CheckIcon, CloseIcon, EditIcon, DeleteIcon,WarningTwoIcon} from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import RedirectButton from "../components/RedirectButton"
import NewsViewMode from "../components/sections/News/NewsViewMode.js"
import NewsEditMode from "../components/sections/News/NewsEditMode.js"
import EditableControls from "../components/EditableControls"
//simbolos dentro da caixa de texto do login
//https://react-icons.github.io/react-icons
import { RiNewspaperLine} from "react-icons/ri";
import { BiEraser} from "react-icons/bi";
  //TODO: 
  function setAppError(error){
    console.log(error)
}

const defaultNews = {
    id:'',
    image: '',
    title: '',
    description: '',
    keywords: [],
    projects: [],
    users: [],
    createdBy: [],
    createdDate: '1900-01-01',
    lastModifBy: [],
    lastModifDate: '',
    visibility: true,
    deleted: true
}


  //**********************************************MAIN FUNCTION !!!!!*************************************************************************** */

function SingleNews( {isAdmin,isLoggedIn,userId,userPriv,errorTopBar="",...props}){
    const { get, post, del, response, loading, error } = useFetch();
    const intl = useIntl();
    const navigate = useNavigate();
    //const [currentId, setCurrentId]=useState("");
    const { id } = useParams();
    console.log(id)
   // setCurrentId(id)

    /**** *******************************************STATE******************************************************** */

  
    const [currentNews, setCurrentNews]=useState(defaultNews);
    //modo edi??ao / visualiza??ao
    const [editMode, setEditClick] = useState(false);
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    
    const [userInCurrentNews, setUserInCurrentNews ]= useState("");
  
    /**** ****************************************FORM*********************************************************** */
    //fu????es que chamos ao submeter o formul??rio de edi????o
    const {register, handleSubmit, formState: {errors}}= useForm();
    //const onSubmit = values => console.log(values); 
    //const handleSubmit(data){setData(data), console.log(data) )};

    //const onSubmit = (data, e) => console.log(data, e);
    async function onSubmit (data, e) {
        const updateUser = await post('users/'+currentNews.id, data)
        if (response.ok) {
            console.log("user atualizado com sucesso ", updateUser);
            setRestResponse("OK");
           
            setAppError("");
           
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setRestResponse("NOK");
           
            setAppError('error_fetch_login_401');
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setRestResponse("NOK");
               
                setAppError(  error );
            }else{
                setRestResponse("NOK");
               
                setAppError(  "error_fetch_generic" );
            }
        }
    }
    
    const onError = (errors, e) => console.log(errors, e);

    /**** ******************************************OTHER BUTTONS********************************************************* */
   
    //fun????o que chamamos ao clicar em "edit"/"hide" edit mode - altera o state de false para true e vice-versa
    const handleEditClick = () => setEditClick(!editMode);

    //fun????o que chamamos ao clicar em cancelar 
    const handleCancelClick = () => setEditClick(!editMode);
    //fun????o que chamamos ao clicar em eliminar 
    const handleDeleteClick= async()=>  {
        const deletedNews = await del('news/'+currentNews.id)
        if (response.ok) {
            console.log("user eliminado/bloqueado com sucesso ", deletedNews);
            setAppError("");
            navigate("/news");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_login_401');
            setRestResponse("NOK");
        }else{
            console.log("houve um erro no fetch " + error)
            if(error && error!=""){
                setAppError(  error );
                setRestResponse("NOK");
            }else{
                setAppError(  "error_fetch_generic" )
                setRestResponse("NOK");
            }
        }
    }


    /**** ******************************************USE EFFECT********************************************************* */
 
    // let currentNews=getUserToShow(id);
    // console.log(currentNews)
    
 useEffect(async()=>{
        console.log("dentro do userEffect");
        setRestResponse("");

        const getnews = await get("news/"+id)
        if (response.ok) {
            console.log(getnews)
            setCurrentNews(getnews);
            setAppError("");
        } else if(response.status==401) {
            console.log("credenciais erradas? " + error)
            setAppError('error_fetch_login_401');
            setRestResponse("NOK");
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
        
        
    },[userInCurrentNews])
    /**
     * use Effect ?? escuta de variaveis para fazer trigger do render
     */
    useEffect(()=>{
        console.log("dentro do segundo  userEffect");
        console.log(editMode);
        setRestResponse("");
    
    },[currentNews,editMode])
    
    /**
     * use effect ?? escuta da vari??vel que obriga ao scroll down
     */
    // useEffect(() => {
    //     window.scrollTo(0,document.body.scrollHeight);
    //   },[])
       

const canEdit=currentNews!=null && !currentNews.deleted && (userId==currentNews.createdBy.id || isAdmin)
const canAssocMyself=currentNews!=null && !currentNews.deleted && !currentNews.users.map((u)=>u.id).includes(userId) && isLoggedIn

/**** ******************************************RENDER / RETURN PRINCIPAL ********************************************************* */
 
if(!currentNews || loading ){
        
    return (<>
       
        <Spinner />
        </>
    )
    }
    if(!currentNews || error ){
        
        return (<>
           
            <Spinner />
            </>
        )
        }
    return (
    <Box>
        <Flex 
        width="100wh"
        minHeight="20vh"
        backgroundColor="teal.400"
        justifyContent={"center"}
        >
            <Heading alignItems={"start"} color ={"white"} ><FormattedMessage id={"news_details"} /> </Heading>
        </Flex>
           
        <Flex
        flexDirection="column"
        width="100wh"
        minHeight="65vh"
        backgroundColor="gray.200"
        justifyContent="start"
        alignItems="center"
        >

        {/* { editMode==false?
        (<Box>
        <Avatar size='2xl' bg="teal.500" mt={"-50%"} mb={10} name={currentNews.firstName + " " + currentNews.lastName} src={currentNews.image} />
        </Box>
        ):null} */}
        {error && 'Error!'}
        {loading && 'Loading...'}

        {!currentNews?
            <Spinner />
        :error?
            <WarningTwoIcon />
    
        :(editMode==false?


            <NewsViewMode  
            canEdit={canEdit}
            canAssocMyself={canAssocMyself}
            isAdmin={isAdmin}
            setUserInCurrentNews={setUserInCurrentNews}
            currentNews= {currentNews} 
            editMode={editMode} 
            handleEditClick={handleEditClick} 
            handleDeleteClick={handleDeleteClick}
            handleCancelClick={handleCancelClick}
            loading={loading}
            />

            : <NewsEditMode 
            canEdit={canEdit}
            isAdmin={isAdmin} 
            currentNews= {currentNews}  
            editMode={editMode}
            handleEditClick={handleEditClick}
            handleDeleteClick={handleDeleteClick}
            handleCancelClick={handleCancelClick} 


            />
            )
        }
        {restResponse=="OK"?
        <Text my={5} color="green"><FormattedMessage id={"sucess_on_updating"} /> </Text>
        : restResponse=="NOK"?
        <Text my={5} color="red"> <FormattedMessage id={"error_on_updating"} />  </Text>
        :null
        }
        <Box mb={30}>
            <RedirectButton path="/news" description={intl.formatMessage({id: "_back_to_news" })} />
        </Box >
        {/* <Avatar name={register.firstName & " " & register.lastName} src={register.image} /> */}
    
        </Flex>
       
    </Box>
    );
}

const mapStateToProps = state => {
    return { userPriv: state.loginOK.userPriv,
            userId:state.loginOK.userId,
            errorTopBar: state.errorMsg.errorTopBar,
            isAdmin:state.loginOK.userPriv==="ADMIN",
            isLoggedIn:state.loginOK.token!=""
    };
  };
export default connect(mapStateToProps,{})(SingleNews)
    // return (
    //     <Box maxW='sm' borderWidth='1px' borderRadius='lg' overflow='hidden'>
    //         <Image src={news.imageUrl} alt={news.title.slice(0,10)} />

    //     </Box>
    // )

    
    
    
