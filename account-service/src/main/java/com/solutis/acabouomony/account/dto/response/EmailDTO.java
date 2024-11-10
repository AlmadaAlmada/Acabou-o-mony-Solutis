package com.solutis.acabouomony.account.dto.response;

import java.util.UUID;

public record EmailDTO(UUID userId, String emailTo, String codeMfa) {}
