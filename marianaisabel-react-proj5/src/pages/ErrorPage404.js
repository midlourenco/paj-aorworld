import React from 'react'

import { FormattedMessage ,useIntl} from "react-intl";

import {
    Flex,
} from "@chakra-ui/react";


function ErrorPage404() {
  return (
    <Flex
    flexDirection="column"
    width="100wh"
    minHeight="83vh"
    backgroundColor="gray.200"
    justifyContent="center"
    alignItems="center"
    >
      <FormattedMessage id={"error_pageNotFound_404"}></FormattedMessage>
   
    </Flex>
  )
}

export default ErrorPage404