//
// Theme variables
//

import { extendTheme } from "@chakra-ui/react";
import { mode, Styles, createBreakpoints } from "@chakra-ui/theme-tools";

const breakpoints = createBreakpoints({
  sm: "320px",
  md: "768px",
  lg: "992px",
  xl: "1200px",
  "2xl": "96em"
});

const config = {
  initialColorMode: "light",
  useSystemColorMode: false
};

const fonts = {
  body:
    "'soleil',-apple-system,BlinkMacSystemFont,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol",
  heading:
    "'soleil',-apple-system,BlinkMacSystemFont,sans-serif,Apple Color Emoji,Segoe UI Emoji,Segoe UI Symbol"
};

const styles = {
  global: props => ({
    // styles for the `body`
    body: {
      bg: mode("white", "blue.900")(props), // #fefeff
      color: mode("fenzodark.400", "whiteAlpha.800")(props), //
      fontSize: "1rem"
    },
    // styles for the `a`
    a: {
      color: "teal.700",
      _hover: {
        textDecoration: "none"
      }
    },
    h1: {
      lineHeight: 1.2,
      fontWeight: "600",
      fontSize: "1.875rem",
      letterSpacing: ".035rem"
    }
  })
};

const colors = {
  black: "#1F2D3D",
  // blue: {
  //   50: "#e1ebff",
  //   100: "#b1c2ff",
  //   200: "#7e99ff",
  //   300: "#4c70ff",
  //   400: "#1a47ff",
  //   500: "#0336FF", //#002ee6
  //   600: "#0024b4",
  //   700: "#001982",
  //   800: "#000e51",
  //   900: "#000521"
  // },
  primary: {
    50: "#e1ebff", // #e1ebff
    100: "#b1c2ff", // #b1c2ff
    200: "#7e99ff",
    300: "#4c70ff",
    400: "#1a47ff",
    500: "#0336FF",
    600: "#0024b4",
    700: "#001982",
    800: "#000e51",
    900: "#000521"
  },
  gray: {
    50: "#737491",
    100: "#F5F8FA",
    200: "#EFF2F5",
    300: "#E4E6EF",
    400: "#B5B5C3",
    500: "#A1A5B7",
    600: "#7E8299",
    700: "#5E6278",
    800: "#3F4254",
    900: "#181C32"
  },
  teal: {
    50: "#E6FFFA",
    100: "#B2F5EA",
    200: "#81E6D9",
    300: "#4FD1C5",
    400: "#38B2AC",
    500: "#319795",
    600: "#2C7A7B",
    700: "#285E61",
    800: "#234E52",
    900: "#1D4044"
  },
  brand: {
    blue: "#0336FF", // #0033FF
    bluedark: "#1D1D42",
    bluedarker: "#141432",
    bluegray: "#1e213e",
    dark_blue: "#201E78",
    black: "#242939",
    dark: "#37384e",
    customGray: "#4a4b65",
    gray: "#737491",
    light_gray: "#F8F8FB",
    smooth_gray: "#F6F9FD",
    silver: "#c9d8e6",
    graysilver: "#8492A6",
    gray_dark: "#4a4b65",
    gray_text: "#37384e",
    danger: "#ef2840",
    softdanger: "#fcd4d9",
    softsuccess: "#def4ed",
    success: "#36b37e"
  },
  fenzodark: {
    50: "#ECEFF9",
    100: "#C9D2ED",
    200: "#A7B5E2",
    300: "#4a5568",
    400: "#38445f",
    500: "#1e213e",
    600: "#10132d",
    700: "#070f2b",
    800: "#070f26",
    900: "#010923" // #010923
  }
};

const components = {
  Heading: {
    baseStyle: props => ({
      fontWeight: "600",
      color: mode("fenzodark.400", "whiteAlpha.900")(props)
    })
  },
  Link: {
    baseStyle: {
      fontWeight: "inherit",
      _hover: {
        textDecoration: "none"
      },
      _focus: {
        boxShadow: "none"
      }
    }
  },
  Button: { // TODO https://chakra-ui-git-fix-typescript-autocomplete.chakra-ui.vercel.app/docs/theming/component-style
    //estilo que todos os botões vão ter em comum
    baseStyle: {
      fontWeight: "600",
      rounded: "4px",
      _focus: {
        outline: "none",
        boxShadow: "none"
      }
    }
  },
  Switch: {
    baseStyle: {
      track: {
        _focus: {
          boxShadow: "none"
        }
      }
    }
  }
};

const zIndices = {
  hide: -1,
  auto: "auto",
  base: 0,
  docked: 10,
  dropdown: 1000,
  sticky: 1100,
  banner: 1200,
  overlay: 1300, // 1300 default
  modal: 1400, // 1400 default
  popover: 1500,
  skipLink: 1600,
  toast: 1700,
  tooltip: 1800 // 1800 default
};

const shadows = {
  xs: "0 0 0 1px rgba(0, 0, 0, 0.05)",
  sm: "0 1px 2px 0 rgba(0, 0, 0, 0.05)",
  default: "0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06)",
  md: "0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06)",
  lg: "0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05)",
  xl:
    "0 20px 25px -5px rgba(0, 0, 0, 0.1), 0 10px 10px -5px rgba(0, 0, 0, 0.04)",
  "2xl": "0 25px 50px -12px rgba(0, 0, 0, 0.25)",
  inner: "inset 0 2px 4px 0 rgba(0, 0, 0, 0.06)",
  outline: "0 0 0 3px rgba(66, 153, 225, 0.5)",
  none: "none"
};

const customTheme = extendTheme({
  config,
  fonts,
  colors,
  styles,
  zIndices,
  components,
  breakpoints
});

export default customTheme;