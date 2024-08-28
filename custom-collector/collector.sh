#!/bin/bash

#
# /*******************************************************************************
# * COPYRIGHT Ericsson 2020
# *
# *
# *
# * The copyright to the computer program(s) herein is the property of
# *
# * Ericsson Inc. The programs may be used and/or copied only with written
# *
# * permission from Ericsson Inc. or in accordance with the terms and
# *
# * conditions stipulated in the agreement/contract under which the
# *
# * program(s) have been supplied.
# ******************************************************************************/
#

function usage {
    echo "The Custom Collector Simulator developed by EDCA team."
    echo
    echo "Usage: ./$(basename "$0")  -c <file> [-a|i] [-d <test-data-dir>]"
    echo
    echo "Available Options:"
    echo
    echo "-c          custom collector properties"
    echo "-a          automation mode"
    echo "-i          interactive mode"
    echo "-h          show usage guide"
    echo
}

function print_error {
    echo "$(basename "$0"): $1"
}

optstring=":c:d:aih"
while getopts ${optstring} arg; do
  case ${arg} in
    h)
      usage
      ;;
    :)
      echo "$0: Must supply an argument to -$OPTARG" >&2
      exit 1
      ;;
    c)
      CONFIG_FILE=$OPTARG
      ;;
    a)
      AUTOMATION="true"
      ;;
    i)
      INTERACTIVE="true"
      ;;
    ?)
      echo "Invalid option: -${OPTARG}"
      exit 2
      ;;
  esac
done

# Missing Configuration
if [ x"${CONFIG_FILE}" == x ]; then
  print_error "-c is a mandatory parameter"
  exit 2
fi

# Missing Execution mode
if [ x"${AUTOMATION}" == x ] && [ x"${INTERACTIVE}" == x ]; then
  print_error "select one mode -a or -i"
  exit 2
fi

echo "Using Configuration: $CONFIG_FILE"

# Run in Automation mode
if [ x"${AUTOMATION}" != x ]; then
  `java -jar target/custom-collector.jar --spring.config.additional-location=$CONFIG_FILE --automation`
fi

# Run in Interactive mode
if [ x"${INTERACTIVE}" != x ]; then
  `java -jar target/custom-collector.jar --spring.config.additional-location=$CONFIG_FILE --interactive`
fi
