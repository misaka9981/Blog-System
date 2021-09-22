 

 
function(hljs) {
  var STRINGS = {      className: 'string',
    begin: '(~)?"', end: '"',
    illegal: '\\n'
  };
  var CONSTANTS = {           className: 'symbol',
    begin: '#[a-zA-Z_]\\w*\\$?'
  };

  return {
    aliases: ['pb', 'pbi'],
    keywords:               'And As Break CallDebugger Case CompilerCase CompilerDefault CompilerElse CompilerEndIf CompilerEndSelect ' +
      'CompilerError CompilerIf CompilerSelect Continue Data DataSection EndDataSection Debug DebugLevel ' +
      'Default Define Dim DisableASM DisableDebugger DisableExplicit Else ElseIf EnableASM ' +
      'EnableDebugger EnableExplicit End EndEnumeration EndIf EndImport EndInterface EndMacro EndProcedure ' +
      'EndSelect EndStructure EndStructureUnion EndWith Enumeration Extends FakeReturn For Next ForEach ' +
      'ForEver Global Gosub Goto If Import ImportC IncludeBinary IncludeFile IncludePath Interface Macro ' +
      'NewList Not Or ProcedureReturn Protected Prototype ' +
      'PrototypeC Read ReDim Repeat Until Restore Return Select Shared Static Step Structure StructureUnion ' +
      'Swap To Wend While With XIncludeFile XOr ' +
      'Procedure ProcedureC ProcedureCDLL ProcedureDLL Declare DeclareC DeclareCDLL DeclareDLL',
    contains: [
             hljs.COMMENT(';', '$', {relevance: 0}),

      {          className: 'function',
        begin: '\\b(Procedure|Declare)(C|CDLL|DLL)?\\b',
        end: '\\(',
        excludeEnd: true,
        returnBegin: true,
        contains: [
          {              className: 'keyword',
            begin: '(Procedure|Declare)(C|CDLL|DLL)?',
            excludeEnd: true
          },
          {              className: 'type',
            begin: '\\.\\w*'
                       },
          hljs.UNDERSCORE_TITLE_MODE          ]
      },
      STRINGS,
      CONSTANTS
    ]
  };
}
