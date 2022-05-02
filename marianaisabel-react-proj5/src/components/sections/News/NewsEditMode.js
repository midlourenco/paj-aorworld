import React from 'react'
import { useState, useEffect, } from "react";
//Redirect replace by Naviagate
import {Navigate, useParams, useNavigate} from 'react-router-dom'
//https://react-hook-form.com/
import { useForm , Controller} from "react-hook-form";
import {FormattedMessage ,useIntl} from "react-intl";

import {
    Flex,
    Heading,
    Input,
    Button,
    InputGroup,
    Stack,
    Box,
    Link,
    Select,
    Avatar,
    Badge,
    FormControl,
    Image,
    VStack,
    FormHelperText,
    CheckboxGroup,
    FormErrorMessage,
    FormLabel,
    RadioGroup,
    Radio,
    Text,
    Spacer,
    Textarea,
    Spinner,
    Checkbox,
    Tooltip,
    InputRightElement,
    Tag,
    TagLabel,TagCloseButton,
} from "@chakra-ui/react";
import { ExternalLinkIcon, EditIcon } from '@chakra-ui/icons';
import useFetch from 'use-http';
import { connect } from 'react-redux'
import {  AddIcon } from '@chakra-ui/icons'
import EditableControls from "../../EditableControls"
  //TODO: 
  function setAppError(error){
    console.log(error)
}
const NewsEditMode=({isAdmin, currentNews,editMode,handleEditClick, handleCancelClick, handleDeleteClick,...props})=>{
    const navigate = useNavigate();
    const intl = useIntl();
    const { get, post, del, response, loading, error } = useFetch();
    
    const [restResponse, setRestResponse]=useState(""); //OK or NOK or ""
    const [input, setInput] = useState([]); //input para as keywords
    const [keywords, setKeywords] = useState(currentNews.keywords);// lista com as keywords
    const [visibility, setVisibility] = useState("public")
    const [users, setUsers] = useState([])
    const [projects, setProjects] = useState([])


    
  
  


    //**********************************************FUNÇOES KEYWORDS*************************************************************************** */
    
    const saveInputKeyword = e => {
      setInput(e.target.value);
    };

    const handleAddNewKeyword = ()=>{
      if(!keywords.includes(input)){
        setKeywords(prevState=>[...prevState, input]);
        setInput("")
      }
    }

    const handleDeleteTag = (keywordToDelete)=>{
      console.log("carreguei na cruz da keyword... a eliminar "+keywordToDelete )
      // keywords.pop(keywordToDelete);
      setKeywords(keywords.filter((k)=>{
        if(k==keywordToDelete){
          return false;
        }
        return true;
      }));
    }
 /**** ****************************************FORM*********************************************************** */
    //fuções que chamos ao submeter o formulário de edição
    const {register,control, handleSubmit, watch, formState: {errors}}= useForm();
    
    async function onSubmit (data, e) {
        data.keywords=keywords;
        if(data.visibility=="public")  data.visibility=true;
        if(data.visibility=="private")  data.visibility=false;
        if(data.projects==0)  data.projects=[];
        if(data.users==0)  data.users=[];

        console.log(data, e);

        const updateNews = await post('news/'+currentNews.id, data)
        if (response.ok) {
            console.log("user atualizado com sucesso ", updateNews);
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


////****************************************************************USE EFFECT*********************** */

useEffect(async()=>{
  console.log("dentro do userEffect");
  setRestResponse("");

  const getUsers = await get("users/")
  if (response.ok) {
      console.log(getUsers)
      setUsers(getUsers);
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


  const getProjects = await get("projects")
  if (response.ok) {
      console.log(getProjects)
      setProjects(getProjects);
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
},[]);
////****************************************************************Lofing management*********************** */



    if(!currentNews || loading ){
        
      return (<>
          <Text>Loading...</Text>
          <Spinner />
          </>
      )
      }

    if(!currentNews || error ){
        
      return (<>
          <Text>Erro...</Text>
          {error}
          <Spinner />
          </>
      )
      }
  
      //faz um mapa/ uam lista só com os users Id que estão associados aos projecto
      const usersIdOnNews =currentNews.users.map((u)=>u.id);
      // const handleCheckedUsers=(userId)=>{
      //   if(usersIdOnNews.includes(userId)){
      //     return true
      //   }
      //   return false
      // }
      //faz um mapa/ uam lista só com as news Id que estão associados aos projecto
      const projectsIdOnNews =currentNews.projects.map((p)=>p.id);
     


    return (<Box>
        <Stack
        flexDir="column"
        mb="2"
        justifyContent="center"
        alignItems="center"
        >
        <Box minW={{ base: "90%", md: "468px" }} mb={5}>
            

            {restResponse==""?
            (<form onSubmit={ handleSubmit(onSubmit,onError )} >
                
                <Flex
                backgroundColor="whiteAlpha.900"
                boxShadow="md"
                minHeight={"300px"}
                p={10}
                flexDirection={"column"}
                justifyContent={"space-between"}
                width={["100%", null, null, "80%"]}
                mt={5}
                mx={[0, null, null, 20]}
                
                >
              <VStack spacing='24px'>
                <FormControl variant='floating' id='title' isInvalid = {errors.title} >                        
                    <Input  {...register("title", {required: true,setValueAs: (v)=> v.trim()})} type="text" id={"title"} defaultValue =  {currentNews.title}  name="title" placeholder={intl.formatMessage({id: 'form_field_first_name'})}/>
                    <FormLabel color={"teal.500"} >{intl.formatMessage({id: 'form_field_title'})}</FormLabel>
                </FormControl>  
                {(errors.title)? 
                (<FormErrorMessage><FormattedMessage id={"error_missing_title"}  ></FormattedMessage></FormErrorMessage>)
                : null 
                }
                <FormControl mt={6}  variant='floating' id='description' > 
                <Textarea {...register("description")}  defaultValue =  {currentNews.description} name="description" placeholder={intl.formatMessage({id: 'form_field_description'})} /> 
                    {/* <Textarea fontSize='lg' placeholder= {currentNews.description} /> 
                 */}
                 <FormLabel color={"teal.500"}  >{intl.formatMessage({id: 'form_field_description'})}</FormLabel>
                </FormControl>

              

                {/* //TODO: floating label for inputgroup - need to fix here */}
                <FormControl  variant='floating' id='keywords' isInvalid = {errors.keyword}>
                <FormLabel color={"teal.500"}  >{intl.formatMessage({id: 'form_field_keywords'})}</FormLabel>
                <InputGroup>
                  <Input {...register("keywords", {deps: keywords})} id='keywords' type="text" placeholder={intl.formatMessage({id: 'form_field_keywords'})}  onChange={saveInputKeyword} value={input} />
                  {/* <FormLabel color={"teal.500"}  >{intl.formatMessage({id: 'form_field_keywords'})}</FormLabel> */}
                    <InputRightElement width="4.5rem">
                      <Button h="1.75rem" size="sm" onClick={handleAddNewKeyword}>
                          <AddIcon color = "gray.400" />
                      </Button>
                    </InputRightElement>
                  </InputGroup>
                
                {keywords && keywords.length ?
                (<Box width={"l"} mr={3} spacing={4}> {
                    keywords.map(k => ( 
                    <Tag key={k} m={2}  colorScheme='teal'>
                    <TagLabel>{k}</TagLabel>
                    <TagCloseButton onClick={()=>handleDeleteTag(k)} />
                  {/* z <TagCloseButton onClick={()=>remove(index)} /> */}
                    </Tag>
                ))} 
                </Box>)
                :null
                }
                {console.log(keywords) }
        
        
                {(errors.keyword)? 
                    (<FormErrorMessage><FormattedMessage id={"error_missing_keyword"}  ></FormattedMessage></FormErrorMessage>)
                    : null 
                }
                </FormControl>


                <FormControl > 

                  <RadioGroup 
                  onChange={setVisibility} 
                  value={visibility}
                  name="visibility"
                  >

                    {/* <InputGroup>   */}
                    <FormLabel color={"teal.500"}  size={"xs"}>{intl.formatMessage({id: 'visibility'})}:</FormLabel>
                      <Stack direction='row'  border={"solid"} borderColor={"gray.200"} p={2} borderRadius={"10px"} fontSize={"sm"}>
                          <Radio  {...register("visibility")}  value="public">{intl.formatMessage({id: 'public'})}</Radio>
                          <Radio  {...register("visibility")}  value="private">{intl.formatMessage({id: 'private'})}</Radio>
                      </Stack>
                      {/* </InputGroup> */}
                      

                  </RadioGroup>
                </FormControl>

                <FormControl  mt={6} variant='floating' id='image' >
                <Input {...register("image")}  type="url"  defaultValue = {currentNews.image}  name="image"  placeholder={intl.formatMessage({id: 'form_field_image'})}/>
                {/* {currentNews.image}   */}
                <FormLabel color={"teal.500"} > {intl.formatMessage({id: 'form_field_image'})}</FormLabel>
                </FormControl>
               
                
                <Image mt={10} name="image2" size={"xs"} src={watch("image") || currentNews.image} fallbackSrc="../images/logo.png"  alt="project_image"/>
       
                </VStack>

                
               
                {/* <Box  direction='row'  border={"solid"} borderColor={"gray.200"} p={2} borderRadius={"10px"} fontSize={"sm"}>
          */}  <FormControl>
             <FormLabel mt={10} color={"teal.500"}  size={"xs"}>{intl.formatMessage({id: 'associated_users'})}:</FormLabel>
           
                <Box  direction='row'  border={"solid"} borderColor={"gray.200"} p={2} borderRadius={"10px"} fontSize={"sm"}>
               
              <Checkbox value="first">first</Checkbox>
              {users.map(u => (
               usersIdOnNews.includes(u.id)?
                <Checkbox   
                {...register("users", {
                  valueAsNumber: true
                  //setValueAs: (v)=> parseInt(v)
              }) }   
                key={u.id}
                value={u.id} 
                colorScheme='teal' 
                m={3} 
                defaultChecked
                >
                    {u.firstName}
                    <Tag size='lg' colorScheme='teal' borderRadius='full' variant="outline">
                    <Avatar
                    src={u.image}
                    size='xs'
                    name={u.firstName}
                    ml={-1}
                    mr={2}
                    />
                    <TagLabel> {u.firstName}</TagLabel>
                </Tag>
                </Checkbox>
                :
                <    Checkbox
                {...register("users", {
                  valueAsNumber: true
                  //setValueAs: (v)=> parseInt(v)
              }) }  
                key={u.id}
                value={u.id} 
                colorScheme='teal' 
                m={3} 
               
                >
               
                <Tag size='lg' colorScheme='teal' borderRadius='full' variant="outline">
                <Avatar
                src={u.image}
                size='xs'
                name={u.firstName}
                ml={-1}
                mr={2}
        
                />
                <TagLabel>{u.firstName}</TagLabel>
                </Tag>
                </Checkbox>
              ))}
              {/* <Checkbox value="second">second</Checkbox>
              <Checkbox value="last">last</Checkbox> */}
          
          </Box>
      </FormControl>
                  {/* </Box>
                 */}
            



          
            <FormControl mt={6}>
                <FormLabel color={"teal.500"}  size={"xs"}>{intl.formatMessage({id: 'associated_projects'})}:</FormLabel>
                <Box  direction='row'  border={"solid"} borderColor={"gray.200"} p={2} borderRadius={"10px"} fontSize={"sm"}>
               
                {/* <CheckboxGroup colorScheme='teal' defaultValue={projectsIdOnNews}> */}
                {projects.map(p => (
                // {...register("checkbox")}
                projectsIdOnNews.includes(p.id)?
              
                <Checkbox  
                {...register("projects", {
                        valueAsNumber: true
                        //setValueAs: (v)=> parseInt(v)
                    }) }  
                    // isChecked={()=>handleCheckedUsers(u.id)}
                    key={p.id}
                    defaultChecked
                    value={p.id} 
                    colorScheme='teal' 
                    m={3} 
                    >
                <Tag size='lg' colorScheme='teal' borderRadius='full' variant="outline">
                    <Avatar
                    src={p.image}
                    size='xs'
                    name={p.title}
                    ml={-1}
                    mr={2}
                    />
                    <TagLabel>{p.title}</TagLabel>
                </Tag>
              </Checkbox>
            :    <Checkbox  
              {...register("projects", {
                      valueAsNumber: true
                      //setValueAs: (v)=> parseInt(v)
                  }) }  
                  // isChecked={()=>handleCheckedUsers(u.id)}
                  key={p.id}
                  value={p.id} 
                  colorScheme='teal' 
                  m={3} 
                  >
              <Tag size='lg' colorScheme='teal' borderRadius='full' variant="outline">
                  <Avatar
                  src={p.image}
                  size='xs'
                  name={p.title}
                  ml={-1}
                  mr={2}
                  />
                  <TagLabel>{p.title}</TagLabel>
              </Tag>
            </Checkbox>



            ))}  
            {/* </CheckboxGroup> */}
            </Box>
            </FormControl>


  
                <Box mt={6}>
                    {/* <IconButton size='sm' icon={<EditIcon />} background="whiteAlpha.900" pt={5} >Editar</IconButton> */}
                    <EditableControls isAdmin={isAdmin} editMode={editMode} handleEditClick={handleEditClick} handleDeleteClick={handleDeleteClick} handleCancelClick={handleCancelClick} />
                    {/* <SubmitButton type={ "submit"} /> */}
                </Box>
               
                </Flex>
            






            
                </form>
                
                
            ):restResponse=="OK"?
                <Text my={5} color="green">{intl.formatMessage({id: 'sucess_rest_response'})}</Text>
                : restResponse=="NOK"?
                  <Text my={5} color="red"> {intl.formatMessage({id: 'error_rest_response'})}: {error} </Text>
                  :null
            }   

           
           
        </Box>
    </Stack>
    </Box>
    )
}


export default NewsEditMode
